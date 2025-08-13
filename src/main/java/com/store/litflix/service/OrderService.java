package com.store.litflix.service;

import com.store.litflix.dto.order.OrderRequestDto;
import com.store.litflix.dto.order.OrderResponseDto;

public interface OrderService {
    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId);
}
