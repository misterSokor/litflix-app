package com.store.litflix.service;

import com.store.litflix.dto.book.BookResponseDto;
import com.store.litflix.dto.book.BookSearchParametersDto;
import com.store.litflix.dto.book.CreateBookRequestDto;
import com.store.litflix.dto.book.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto requestDto);

    List<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto findById(Long id);

    List<BookResponseDto> search(BookSearchParametersDto searchParametersDto);

    BookResponseDto updateBook(Long id, UpdateBookRequestDto requestDto);

    void deleteById(Long id);
}
