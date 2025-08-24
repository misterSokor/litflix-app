package com.store.litflix.mapper;

import com.store.litflix.config.MapperConfig;
import com.store.litflix.dto.order.OrderResponseDto;
import com.store.litflix.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class })
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toDto(Order order);
}
