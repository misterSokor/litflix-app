package com.store.litflix.service;

import com.store.litflix.dto.cart.ShoppingCartRequestDto;
import com.store.litflix.dto.cart.ShoppingCartResponseDto;

public interface CartService {
    ShoppingCartResponseDto addBookToCart(ShoppingCartRequestDto requestDto);

    ShoppingCartResponseDto getCartInfo();

    ShoppingCartResponseDto updateCartItemQuantity(Long cartItemId, int quantity);

    void removeCartItem(Long cartItemId);
}
