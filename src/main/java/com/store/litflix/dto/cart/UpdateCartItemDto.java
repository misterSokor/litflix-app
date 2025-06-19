package com.store.litflix.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartItemDto {
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
