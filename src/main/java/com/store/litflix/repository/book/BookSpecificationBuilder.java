package com.store.litflix.repository.book;

import com.store.litflix.dto.BookSearchParametersDto;
import com.store.litflix.model.Book;
import com.store.litflix.repository.SpecificationBuilder;
import com.store.litflix.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto params) {
        Specification<Book> spec = Specification.where(null);
        if (params.author() != null && params.author().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(params.author()));
        }

        if (params.title() != null && params.title().length > 0) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(params.title()));
        }
        return spec;
    }
}
