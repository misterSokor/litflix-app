package com.store.litflix.mapper;

import com.store.litflix.config.MapperConfig;
import com.store.litflix.dto.category.CategoryDto;
import com.store.litflix.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryDto categoryDto);

    void updateCategoryFromDto(CategoryDto dto, @MappingTarget Category entity);
}
