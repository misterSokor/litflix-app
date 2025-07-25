package com.store.litflix.controller;

import com.store.litflix.dto.cart.CartItemRequestDto;
import com.store.litflix.dto.cart.ShoppingCartResponseDto;
import com.store.litflix.dto.cart.UpdateCartItemDto;
import com.store.litflix.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Shopping Cart", description = "Operations related to the shopping cart")
public class CartController {

    private final CartService cartService;

    @Operation(
            summary = "Add a book to the cart",
            description = "Adds a book to the shopping cart."
                          + " If the book already exists, it increases the quantity."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponseDto addBookToCart(
            @RequestBody @Valid CartItemRequestDto request,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        return cartService.addBookToCart(request, userId);
    }

    @Operation(
            summary = "Get current shopping cart",
            description = "Returns the current user's shopping cart"
                          + " with all items and quantities"
    )
    @GetMapping
    public ShoppingCartResponseDto getCartInfo(Authentication authentication) {
        Long userId = extractUserId(authentication);
        return cartService.getCartInfo(userId);
    }

    @Operation(
            summary = "Update quantity of a cart item",
            description = "Updates the quantity of a specific book in the shopping cart"
    )
    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartResponseDto updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemDto requestDto,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        return cartService.updateCartItemQuantity(cartItemId,
                requestDto.getQuantity(),
                userId);
    }

    @Operation(
            summary = "Remove a book from the shopping cart",
            description = "Deletes a specific cart item by its ID."
                          + " Only the cart owner can perform this action."
    )
    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItem(@PathVariable Long cartItemId,
                               Authentication authentication) {
        Long userId = extractUserId(authentication);
        cartService.removeCartItem(cartItemId, userId);
    }

    private Long extractUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }
}
