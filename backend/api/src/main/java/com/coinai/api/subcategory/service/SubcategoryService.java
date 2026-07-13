package com.coinai.api.subcategory.service;

import com.coinai.api.subcategory.dto.request.CreateSubcategoryRequest;
import com.coinai.api.subcategory.dto.request.UpdateSubcategoryRequest;
import com.coinai.api.subcategory.dto.response.SubcategoryResponse;

import java.util.List;
import java.util.UUID;

public interface SubcategoryService {

    SubcategoryResponse create(
            CreateSubcategoryRequest request
    );

    List<SubcategoryResponse> findAll();

    SubcategoryResponse update(
            UUID id,
            UpdateSubcategoryRequest request
    );

    void delete(UUID id);

}