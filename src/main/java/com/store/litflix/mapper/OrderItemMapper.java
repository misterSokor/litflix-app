package com.store.litflix.mapper;

import com.store.litflix.config.MapperConfig;
import com.store.litflix.dto.order.OrderItemDto;
import com.store.litflix.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem item);
}
