package com.gestor_finanzas.service;

import com.gestor_finanzas.dto.CategoryRequest;
import com.gestor_finanzas.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);
}
