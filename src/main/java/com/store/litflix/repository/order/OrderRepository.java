package com.store.litflix.repository.order;

import com.store.litflix.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByUserId(Long userId);

    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

}
