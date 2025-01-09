package com.store.litflix.service;

import com.store.litflix.dto.book.BookDto;
import com.store.litflix.dto.book.BookSearchParametersDto;
import com.store.litflix.dto.book.CreateBookRequestDto;
import com.store.litflix.dto.book.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto);

    BookDto updateBook(Long id, UpdateBookRequestDto requestDto);

    void deleteById(Long id);
}
