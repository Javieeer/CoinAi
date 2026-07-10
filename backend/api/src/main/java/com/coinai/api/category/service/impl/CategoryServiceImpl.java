package com.coinai.api.category.service.impl;

import com.coinai.api.category.dto.request.CreateCategoryRequest;
import com.coinai.api.category.dto.response.CategoryResponse;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.mapper.CategoryMapper;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.category.service.CategoryService;
import com.coinai.api.common.exception.CategoryAlreadyExistsException;
import com.coinai.api.movement.MovementType;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (categoryRepository.existsByUserIdAndNameIgnoreCase(user.getId(), request.getName())) {
            throw new CategoryAlreadyExistsException();
        }

        Category category = categoryMapper.toEntity(request);

        category.setUser(user);
        category.setDefault(false);
        category.setCreatedAt(LocalDateTime.now());

        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);

    }

    @Override
    public List<CategoryResponse> findAll(MovementType movementType) {

        User user = authenticatedUserService.getCurrentUser();

        List<Category> categories = new ArrayList<>();

        if (movementType == null) {

            categories.addAll(categoryRepository.findByIsDefaultTrue());
            categories.addAll(categoryRepository.findByUserId(user.getId()));

        } else {

            categories.addAll(
                    categoryRepository.findByIsDefaultTrueAndMovementType(movementType)
            );

            categories.addAll(
                    categoryRepository.findByUserIdAndMovementType(
                            user.getId(),
                            movementType
                    )
            );

        }

        categories.sort(
                Comparator.comparing(Category::getName, String.CASE_INSENSITIVE_ORDER)
        );

        return categoryMapper.toResponseList(categories);

    }

}