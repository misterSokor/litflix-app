package com.store.litflix.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreateRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public CategoryCreateRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
