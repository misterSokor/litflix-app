package com.store.litflix.repository.book.spec;

import com.store.litflix.model.Book;
import com.store.litflix.repository.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private final String key = "author";

    @Override
    public String getSearchKey() {
        return key;
    }

    @Override
    public Specification<Book> getSpecification(String[] searchParameters) {
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return root.get(key).in(Arrays.stream(searchParameters).toArray());
            }
        };
    }
}
