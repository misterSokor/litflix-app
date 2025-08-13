package com.store.litflix.dto.order;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrderRequestDto {
    private String shippingAddress;
}
