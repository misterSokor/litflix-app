package com.store.litflix.service.impl;

import com.store.litflix.dto.category.CategoryDto;
import com.store.litflix.exception.EntityNotFoundException;
import com.store.litflix.mapper.CategoryMapper;
import com.store.litflix.model.Category;
import com.store.litflix.repository.category.CategoryRepository;
import com.store.litflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto getById(Long id) {
        Category categoryById = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not "
                                                               + "found with id " + id));
        return categoryMapper.toDto(categoryById);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not "
                                                               + "found with id " + id));
        categoryMapper.updateCategoryFromDto(categoryDto, category);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not "
                                                               + "found with id " + id));
        categoryRepository.delete(category);
    }
}
