package com.store.litflix.service.impl;

import com.store.litflix.dto.order.OrderItemDto;
import com.store.litflix.dto.order.OrderRequestDto;
import com.store.litflix.dto.order.OrderResponseDto;
import com.store.litflix.exception.EntityNotFoundException;
import com.store.litflix.exception.OrderProcessingException;
import com.store.litflix.mapper.OrderItemMapper;
import com.store.litflix.mapper.OrderMapper;
import com.store.litflix.model.CartItem;
import com.store.litflix.model.Order;
import com.store.litflix.model.OrderItem;
import com.store.litflix.model.ShoppingCart;
import com.store.litflix.model.Status;
import com.store.litflix.model.User;
import com.store.litflix.repository.cart.CartRepository;
import com.store.litflix.repository.order.OrderRepository;
import com.store.litflix.repository.user.UserRepository;
import com.store.litflix.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, Long userId) {
        User user =
                userRepository.findById(userId).orElseThrow(
                        () -> new EntityNotFoundException("User not found: " + userId));
        String shippingAddress = orderRequestDto.getShippingAddress();

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.PENDING);
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setShippingAddress(shippingAddress);

        BigDecimal totalAmount = BigDecimal.ZERO;
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart not found for user: " + userId));
        if (cart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Cannot place order with empty cart");
        } else {
            for (CartItem cartItem : cart.getCartItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setBook(cartItem.getBook());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getBook().getPrice());
                order.getOrderItems().add(orderItem);

                totalAmount = totalAmount.add(
                        orderItem.getPrice()
                                .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                                .setScale(2, RoundingMode.HALF_UP)
                );
            }
        }

        order.setTotal(totalAmount.setScale(2, RoundingMode.HALF_UP));

        orderRepository.save(order);
        cart.getCartItems().clear();
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findOrdersByUserId(userId);

        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId, Long userId) {
        Order order = findOrderByIdAndUserId(orderId, userId);

        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long userId, Long orderItemId) {
        Order order = findOrderByIdAndUserId(orderId, userId);

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getId().equals(orderItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order item with id: " + orderItemId + " not found in order with id: "
                        + orderId
                ));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, Status status,
                                              Long userId) {
        Order order = findOrderByIdAndUserId(orderId, userId);

        order.setStatus(status);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private Order findOrderByIdAndUserId(Long orderId, Long userId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order with id: " + orderId + " not found or does not "
                        + "belong to the user with id: " + userId
                ));
    }
}
