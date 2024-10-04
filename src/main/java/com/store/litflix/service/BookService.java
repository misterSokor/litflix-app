package com.store.litflix.service;

import com.store.litflix.dto.BookDto;
import com.store.litflix.dto.BookSearchParametersDto;
import com.store.litflix.dto.CreateBookRequestDto;
import com.store.litflix.dto.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable);

    BookDto updateBook(Long id, UpdateBookRequestDto requestDto);

    void deleteById(Long id);
}
