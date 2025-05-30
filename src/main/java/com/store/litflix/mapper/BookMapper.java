package com.store.litflix.mapper;

import com.store.litflix.config.MapperConfig;
import com.store.litflix.dto.book.BookDto;
import com.store.litflix.dto.book.BookDtoWithoutCategoryIds;
import com.store.litflix.dto.book.CreateBookRequestDto;
import com.store.litflix.dto.book.UpdateBookRequestDto;
import com.store.litflix.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto bookDto);

    Book updateModelFromDto(UpdateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
