package com.store.litflix.service;

import com.store.litflix.dto.order.OrderItemDto;
import com.store.litflix.dto.order.OrderRequestDto;
import com.store.litflix.dto.order.OrderResponseDto;
import com.store.litflix.model.Status;
import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId);

    List<OrderResponseDto> getOrderHistory(Long userId);

    List<OrderItemDto> getOrderItems(Long orderId, Long userId);

    OrderItemDto getOrderItemById(Long orderId, Long userId, Long orderItemId);

    OrderResponseDto updateOrderStatus(Long orderId, Status status,
                                       Long userId);
}
