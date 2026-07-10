package com.coinai.api.category.controller;

import java.util.List;
import com.coinai.api.category.dto.request.CreateCategoryRequest;
import com.coinai.api.category.dto.response.CategoryResponse;
import com.coinai.api.category.service.CategoryService;
import com.coinai.api.movement.MovementType;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(
            @Valid @RequestBody CreateCategoryRequest request
    ) {

        System.out.println("=== ENTRE AL CONTROLLER CATEGORY ===");
        return categoryService.create(request);

    }

    @GetMapping
    public List<CategoryResponse> findAll(

            @RequestParam(required = false)
            MovementType movementType

    ) {

        return categoryService.findAll(movementType);

    }

}