package com.store.litflix.mapper;

import com.store.litflix.config.MapperConfig;
import com.store.litflix.dto.book.BookDtoWithoutCategoryIds;
import com.store.litflix.dto.book.BookResponseDto;
import com.store.litflix.dto.book.CreateBookRequestDto;
import com.store.litflix.dto.book.UpdateBookRequestDto;
import com.store.litflix.model.Book;
import com.store.litflix.model.Category;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", source = "categories", qualifiedByName = "mapCategoryIds")
    BookResponseDto toDto(Book book);

    @Named("mapCategoryIds")
    static List<Long> mapCategoryIds(Set<Category> categories) {
        return categories == null ? null :
                categories.stream().map(com.store.litflix.model.Category::getId).toList();
    }

    Book toModel(CreateBookRequestDto bookDto);

    Book updateModelFromDto(UpdateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
