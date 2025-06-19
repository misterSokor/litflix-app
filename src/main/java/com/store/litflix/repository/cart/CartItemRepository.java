package com.store.litflix.repository.cart;

import com.store.litflix.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartItemRepository extends JpaRepository<CartItem, Long>,
        JpaSpecificationExecutor<CartItem> {
    CartItem findByIdAndShoppingCartId(Long cartItemId, Long shoppingCartId);
}
