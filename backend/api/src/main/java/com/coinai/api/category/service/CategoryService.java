package com.coinai.api.category.service;

import com.coinai.api.category.dto.request.CreateCategoryRequest;
import com.coinai.api.category.dto.request.UpdateCategoryRequest;
import com.coinai.api.category.dto.response.CategoryResponse;
import com.coinai.api.movement.MovementType;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse create(CreateCategoryRequest request);

    List<CategoryResponse> findAll(MovementType movementType);

    CategoryResponse update(UUID id, UpdateCategoryRequest request);

    void delete(UUID id);
}