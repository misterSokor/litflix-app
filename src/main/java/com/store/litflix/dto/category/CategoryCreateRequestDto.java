package com.store.litflix.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryCreateRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
