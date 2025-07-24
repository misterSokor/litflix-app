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
import jakarta.transaction.Transactional;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto addBookToCart(CartItemRequestDto requestDto,
                                                 Long userId) {
        User user =
                userRepository.findById(userId).orElseThrow(
                        () -> new EntityNotFoundException("User not found: " + userId));
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "The book with ID " + requestDto.getBookId() + " was not found.")
        );

        ShoppingCart shoppingCart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createShoppingCart(user));

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);

        shoppingCart.getCartItems().add(cartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getCartInfo(Long userId) {
        ShoppingCart shoppingCart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found"));

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateCartItemQuantity(Long cartItemId,
                                                          int quantity,
                                                          Long userId) {
        CartItem cartItem = getCartItemByIdForCurrentUser(cartItemId, userId);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        ShoppingCart updatedCart = cartItem.getShoppingCart();
        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public void removeCartItem(Long cartItemId, Long userId) {
        CartItem cartItem = getCartItemByIdForCurrentUser(cartItemId, userId);
        cartItem.getShoppingCart().getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());

        return cartRepository.save(cart);
    }

    private CartItem getCartItemByIdForCurrentUser(Long cartItemId,
                                                   Long userId) {
        ShoppingCart cart =
                cartRepository.findByUserId(userId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Shopping cart not found"));

        CartItem cartItem =
                cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                                cart.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
        return cartItem;
    }
}
