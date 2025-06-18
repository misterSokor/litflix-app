package com.store.litflix.exception;

public class CartItemAccessDeniedException extends RuntimeException {
    public CartItemAccessDeniedException(String message) {
        super(message);
    }
}
