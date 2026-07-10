package com.coinai.api.category.service;

import com.coinai.api.category.dto.request.CreateCategoryRequest;
import com.coinai.api.category.dto.response.CategoryResponse;
import com.coinai.api.movement.MovementType;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CreateCategoryRequest request);

    List<CategoryResponse> findAll(MovementType movementType);

}