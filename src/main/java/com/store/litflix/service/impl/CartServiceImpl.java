package com.store.litflix.service.impl;

import com.store.litflix.dto.cart.CartItemRequestDto;
import com.store.litflix.dto.cart.ShoppingCartResponseDto;
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
import java.util.HashSet;
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
    public ShoppingCartResponseDto addBookToCart(CartItemRequestDto requestDto) {
        User user = getCurrentUser();
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("The book was not found.")
        );

        int quantity = requestDto.getQuantity();

        ShoppingCart shoppingCart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createShoppingCart(user));

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);

        shoppingCart.getCartItems().add(cartItem);
        user.setShoppingCart(shoppingCart);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    @Override
    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());

        return cartRepository.save(cart);
    }

    @Override
    public ShoppingCartResponseDto getCartInfo() {
        User user = getCurrentUser();

        ShoppingCart shoppingCart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found"));

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateCartItemQuantity(Long cartItemId, int quantity) {
        User user = getCurrentUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

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
        User user = getCurrentUser();
        ShoppingCart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found"));

        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId, cart.getId());
        if (cartItem == null) {
            throw new EntityNotFoundException("Cart item not found");
        }

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You are not allowed to delete this cart item.");
        }

        cart.getCartItems().remove(cartItem);

        cartItemRepository.delete(cartItem);
    }
}
