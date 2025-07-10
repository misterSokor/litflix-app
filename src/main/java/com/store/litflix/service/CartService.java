package com.store.litflix.service;

import com.store.litflix.dto.cart.CartItemRequestDto;
import com.store.litflix.dto.cart.ShoppingCartResponseDto;
import com.store.litflix.model.ShoppingCart;
import com.store.litflix.model.User;

public interface CartService {
    ShoppingCartResponseDto addBookToCart(CartItemRequestDto requestDto);

    ShoppingCartResponseDto getCartInfo();

    ShoppingCartResponseDto updateCartItemQuantity(Long cartItemId, int quantity);

    void removeCartItem(Long cartItemId);

    ShoppingCart createShoppingCart(User user);
}
