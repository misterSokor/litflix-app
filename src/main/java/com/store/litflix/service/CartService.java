package com.store.litflix.service;

import com.store.litflix.dto.cart.CartItemRequestDto;
import com.store.litflix.dto.cart.ShoppingCartResponseDto;

public interface CartService {
    ShoppingCartResponseDto addBookToCart(CartItemRequestDto requestDto);

    ShoppingCartResponseDto getCartInfo();

    ShoppingCartResponseDto updateCartItemQuantity(Long cartItemId, int quantity);

    void removeCartItem(Long cartItemId);
}
