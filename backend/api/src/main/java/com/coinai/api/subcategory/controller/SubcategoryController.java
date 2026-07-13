package com.coinai.api.subcategory.controller;

import com.coinai.api.subcategory.dto.request.CreateSubcategoryRequest;
import com.coinai.api.subcategory.dto.request.UpdateSubcategoryRequest;
import com.coinai.api.subcategory.dto.response.SubcategoryResponse;
import com.coinai.api.subcategory.service.SubcategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubcategoryResponse create(
            @Valid @RequestBody CreateSubcategoryRequest request
    ) {

        return subcategoryService.create(request);

    }

    @GetMapping
    public List<SubcategoryResponse> findAll() {

        return subcategoryService.findAll();

    }

    @PutMapping("/{id}")
    public SubcategoryResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSubcategoryRequest request
    ) {

        return subcategoryService.update(id, request);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {

        subcategoryService.delete(id);

    }

}