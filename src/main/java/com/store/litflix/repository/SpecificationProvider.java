package com.store.litflix.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getSearchKey();

    Specification<T> getSpecification(String[] searchParameters);
}
