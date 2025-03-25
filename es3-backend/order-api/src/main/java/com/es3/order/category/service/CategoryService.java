package com.es3.order.category.service;

import com.es3.order.category.domain.Category;
import com.es3.order.category.domain.CategoryRepository;
import com.es3.order.category.dto.CategoryDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(CategoryDto categoryDto) {
        categoryRepository.save(Category.create(categoryDto));
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(CategoryDto::fromEntity).toList();
    }
}
