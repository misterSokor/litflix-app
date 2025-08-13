package com.store.litflix.service.impl;

import com.store.litflix.dto.order.OrderRequestDto;
import com.store.litflix.dto.order.OrderResponseDto;
import com.store.litflix.exception.EntityNotFoundException;
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

        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart not found for user: " + userId));
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : cart.getCartItems()) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setBook(ci.getBook());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getBook().getPrice());
            order.getOrderItems().add(oi);

            total = total.add(
                    oi.getPrice()
                            .multiply(BigDecimal.valueOf(oi.getQuantity()))
                            .setScale(2, RoundingMode.HALF_UP)
            );
        }

        order.setTotal(total.setScale(2, RoundingMode.HALF_UP));

        Order saved = orderRepository.save(order);
        cart.getCartItems().clear();
        return orderMapper.toDto(saved);
    }
}
