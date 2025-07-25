package com.store.litflix.service;

import com.store.litflix.dto.cart.CartItemRequestDto;
import com.store.litflix.dto.cart.ShoppingCartResponseDto;
import com.store.litflix.model.ShoppingCart;
import com.store.litflix.model.User;

public interface CartService {
    ShoppingCartResponseDto addBookToCart(CartItemRequestDto requestDto,
                                          Long userId);

    ShoppingCartResponseDto getCartInfo(Long userId);

    ShoppingCartResponseDto updateCartItemQuantity(Long cartItemId,
                                                   int quantity,
                                                   Long userId);

    void removeCartItem(Long cartItemId, Long userId);

    ShoppingCart createShoppingCart(User user);
}
