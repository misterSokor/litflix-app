package com.store.litflix.service.impl;

import com.store.litflix.dto.cart.ShoppingCartRequestDto;
import com.store.litflix.dto.cart.ShoppingCartResponseDto;
import com.store.litflix.exception.CartItemAccessDeniedException;
import com.store.litflix.exception.CartItemNotFoundException;
import com.store.litflix.exception.EntityNotFoundException;
import com.store.litflix.mapper.ShoppingCartMapper;
import com.store.litflix.model.Book;
import com.store.litflix.model.CartItem;
import com.store.litflix.model.ShoppingCart;
import com.store.litflix.model.User;
import com.store.litflix.repository.book.BookRepository;
import com.store.litflix.repository.cart.CartItemRepository;
import com.store.litflix.repository.cart.CartRepository;
import com.store.litflix.repository.user.UserRepository;
import com.store.litflix.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto addBookToCart(ShoppingCartRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new RuntimeException("The book wos not found.")
        );

        int quantity = requestDto.getQuantity();

        ShoppingCart shoppingCart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(shoppingCart);
        cartItem = cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);

        ShoppingCart saved = cartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(saved);
    }

    @Override
    public ShoppingCartResponseDto getCartInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart shoppingCart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateCartItemQuantity(Long cartItemId, int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getShoppingCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to modify this item.");
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        ShoppingCart updatedCart = cartItem.getShoppingCart();
        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException(
                        "Cart item not found"));

        ShoppingCart cart = cartItem.getShoppingCart();

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new CartItemAccessDeniedException(
                    "You are not allowed to delete this cart item.");
        }

        cart.getCartItems().remove(cartItem);

        cartItemRepository.delete(cartItem);
    }
}
