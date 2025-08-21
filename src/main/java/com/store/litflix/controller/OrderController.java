package com.store.litflix.controller;

import com.store.litflix.dto.order.OrderItemDto;
import com.store.litflix.dto.order.OrderRequestDto;
import com.store.litflix.dto.order.OrderResponseDto;
import com.store.litflix.dto.order.OrderStatusUpdateDto;
import com.store.litflix.model.User;
import com.store.litflix.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    @Operation(
            summary = "Get order history",
            description = "Retrieves all past orders of the current user."
    )
    @GetMapping
    public List<OrderResponseDto> getOrderHistory(Authentication authentication) {
        Long userId = extractUserId(authentication);
        return orderService.getOrderHistory(userId);
    }

    @Operation(summary = "Get items of an order",
            description = "Returns all items for the given order if "
                          + "it belongs to the current user.")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> orderItems(@PathVariable Long orderId,
                                         Authentication authentication) {
        Long userId = extractUserId(authentication);
        return orderService.getOrderItems(orderId, userId);
    }

    @Operation(
            summary = "Get a specific order item",
            description = "Returns a specific order item by its ID if it"
                          + " belongs to the current user."
    )
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long itemId,
                                     Authentication authentication) {
        Long userId = extractUserId(authentication);
        return orderService.getOrderItemById(orderId, userId, itemId);
    }

    @Operation(
            summary = "Update order status",
            description = "Updates the status of an order if it belongs"
                          + " to the current user."
    )
    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponseDto updateOrderStatus(@PathVariable Long orderId,
                        @RequestBody @Valid OrderStatusUpdateDto statusUpdateDto,
                                              Authentication authentication) {
        Long userId = extractUserId(authentication);
        return orderService.updateOrderStatus(orderId, statusUpdateDto.status(),
                userId);
    }
}
