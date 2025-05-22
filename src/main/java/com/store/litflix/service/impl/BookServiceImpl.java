package com.store.litflix.service.impl;

import com.store.litflix.dto.book.BookDto;
import com.store.litflix.dto.book.BookSearchParametersDto;
import com.store.litflix.dto.book.CreateBookRequestDto;
import com.store.litflix.dto.book.UpdateBookRequestDto;
import com.store.litflix.exception.EntityNotFoundException;
import com.store.litflix.mapper.BookMapper;
import com.store.litflix.model.Book;
import com.store.litflix.model.Category;
import com.store.litflix.repository.book.BookRepository;
import com.store.litflix.repository.book.BookSpecificationBuilder;
import com.store.litflix.repository.category.CategoryRepository;
import com.store.litflix.service.BookService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        if (requestDto.getCategoryIds() != null && !requestDto.getCategoryIds().isEmpty()) {
            Set<Category> categories = requestDto.getCategoryIds().stream()
                    .map(id -> categoryRepository.findById(id)
                            .orElseThrow(() ->
                                    new EntityNotFoundException(
                                            "Category not found with id " + id)))
                    .collect(Collectors.toSet());

            book.setCategories(categories);
        }

        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book bookById = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not "
                                                               + "found with id " + id));
        return bookMapper.toDto(bookById);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParameters) {
        Specification<Book> bookSpecification =
                specificationBuilder.build(searchParameters);
        return bookRepository
                .findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto updateBook(Long id, UpdateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not "
                                                               + "found with id " + id));
        bookMapper.updateModelFromDto(requestDto, book);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}

