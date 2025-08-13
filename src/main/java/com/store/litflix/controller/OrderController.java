package com.store.litflix.controller;

import com.store.litflix.dto.order.OrderRequestDto;
import com.store.litflix.dto.order.OrderResponseDto;
import com.store.litflix.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(
            summary = "Place an order",
            description = "Places a new order for the current user."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto placeOrder(@RequestBody @Valid OrderRequestDto orderRequest,
                                       Authentication authentication) {
        Long userId = extractUserId(authentication);
        return orderService.placeOrder(orderRequest, userId);
    }

    private Long extractUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }
}
