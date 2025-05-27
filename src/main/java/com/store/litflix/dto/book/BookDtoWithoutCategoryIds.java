package com.store.litflix.dto.book;

import com.store.litflix.dto.category.CategoryNameDescriptionDto;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class BookDtoWithoutCategoryIds {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<CategoryNameDescriptionDto> categories;
}
