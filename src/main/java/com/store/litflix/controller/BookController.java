package com.store.litflix.controller;

import com.store.litflix.dto.BookDto;
import com.store.litflix.dto.BookSearchParametersDto;
import com.store.litflix.dto.CreateBookRequestDto;
import com.store.litflix.dto.UpdateBookRequestDto;
import com.store.litflix.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController extends HttpServlet {
    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Creates a new book",
            description = "creates a new book")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "finds book by its id",
            description = "finds book by its id")
    public BookDto getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    @Operation(summary = "finds all books sorted and divided into pages",
            description = "finds all books sorted and divided into pages")
    public List<BookDto> getAllBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "finds books by specific criteria",
            description = "finds books by specific criteria")
    public List<BookDto> searchBooks(@RequestParam(required = false) String[] title,
                                     @RequestParam(required = false) String[] author) {
        BookSearchParametersDto bookSearchParametersDto =
                new BookSearchParametersDto(title, author);
        return bookService.search(bookSearchParametersDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "method is used to update existing book",
            description = "method is used to update existing book")
    public BookDto updateBook(@PathVariable Long id,
                              @RequestBody UpdateBookRequestDto requestDto) {
        return bookService.updateBook(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "this method is used to delete book",
            description = "this method is used to delete book by changing its"
                          + "  status")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
