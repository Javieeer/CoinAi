package com.coinai.api.category.service.impl;

import com.coinai.api.category.dto.request.CreateCategoryRequest;
import com.coinai.api.category.dto.request.UpdateCategoryRequest;
import com.coinai.api.category.dto.response.CategoryResponse;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.enums.CategoryOrigin;
import com.coinai.api.category.exception.CategoryAlreadyExistsException;
import com.coinai.api.category.exception.CategoryDeletionNotAllowedException;
import com.coinai.api.category.exception.CategoryModificationNotAllowedException;
import com.coinai.api.category.exception.CategoryNotFoundException;
import com.coinai.api.category.mapper.CategoryMapper;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.category.service.CategoryService;
import com.coinai.api.movement.MovementType;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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
        category.setCreatedAt(LocalDateTime.now());

        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);

    }

    @Override
    public List<CategoryResponse> findAll(MovementType movementType) {

        User user = authenticatedUserService.getCurrentUser();

        List<Category> categories = new ArrayList<>();

        if (movementType == null) {

            categories.addAll(categoryRepository.findByOrigin(CategoryOrigin.SYSTEM));
            categories.addAll(categoryRepository.findByUserId(user.getId()));

        } else {

            categories.addAll(
                    categoryRepository.findByOriginAndMovementType(
                        CategoryOrigin.SYSTEM,
                        movementType
                    )
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

    @Override
    public CategoryResponse update(UUID id, UpdateCategoryRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        Category category = categoryRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(CategoryNotFoundException::new);


        boolean duplicated = categoryRepository.existsByUserIdAndNameIgnoreCase(
                user.getId(),
                request.getName()
        );

        if (duplicated && !category.getName().equalsIgnoreCase(request.getName())) {
            throw new CategoryAlreadyExistsException();
        }

        category.setName(request.getName());
        category.setIcon(request.getIcon());
        category.setColor(request.getColor());
        category.setMovementType(request.getMovementType());
        category.setOrigin(CategoryOrigin.CUSTOM);

        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);

    }

    @Override
    public void delete(UUID id) {

        User user = authenticatedUserService.getCurrentUser();

        Category category = categoryRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(CategoryNotFoundException::new);


        categoryRepository.delete(category);

    }
    
}