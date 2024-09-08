package com.store.litflix.repository.book;

import com.store.litflix.model.Book;
import com.store.litflix.repository.SpecificationProvider;
import com.store.litflix.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecProviders.stream()
                .filter(provider -> provider.getSearchKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "No provider found for key: " + key));
    }
}
