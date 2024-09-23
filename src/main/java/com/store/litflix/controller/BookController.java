package com.store.litflix.controller;

import com.store.litflix.dto.BookDto;
import com.store.litflix.dto.BookSearchParametersDto;
import com.store.litflix.dto.CreateBookRequestDto;
import com.store.litflix.dto.UpdateBookRequestDto;
import com.store.litflix.service.BookService;
import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController extends HttpServlet {
    private final BookService bookService;

    @PostMapping
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/search")
    public List<BookDto> searchBooks(@RequestParam(required = false) String[] title,
                                     @RequestParam(required = false) String[] author) {
        BookSearchParametersDto bookSearchParametersDto =
                new BookSearchParametersDto(title, author);
        return bookService.search(bookSearchParametersDto);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id,
                              @RequestBody UpdateBookRequestDto requestDto) {
        return bookService.updateBook(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
