package com.coinai.api.category.service.impl;

import com.coinai.api.category.dto.request.CreateCategoryRequest;
import com.coinai.api.category.dto.response.CategoryResponse;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.mapper.CategoryMapper;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.common.exception.CategoryAlreadyExistsException;
import com.coinai.api.movement.MovementType;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private AuthenticatedUserService authenticatedUserService;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
        void shouldCreateCategorySuccessfully() {

                User user = User.builder()
                        .id(UUID.randomUUID())
                        .build();

                CreateCategoryRequest request = new CreateCategoryRequest();
                request.setName("Mercado");
                request.setMovementType(MovementType.EXPENSE);
                request.setIcon("shopping-cart");
                request.setColor("#4CAF50");

                Category category = Category.builder()
                        .name("Mercado")
                        .movementType(MovementType.EXPENSE)
                        .user(user)
                        .build();

                CategoryResponse response = CategoryResponse.builder()
                        .name("Mercado")
                        .movementType(MovementType.EXPENSE)
                        .icon("shopping-cart")
                        .color("#4CAF50")
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);
                when(categoryRepository.existsByUserIdAndNameIgnoreCase(user.getId(), "Mercado"))
                        .thenReturn(false);
                when(categoryMapper.toEntity(request)).thenReturn(category);
                when(categoryRepository.save(any(Category.class))).thenReturn(category);
                when(categoryMapper.toResponse(category)).thenReturn(response);

                CategoryResponse result = categoryService.create(request);

                assertEquals("Mercado", result.getName());
                assertEquals(MovementType.EXPENSE, result.getMovementType());

        }

    @Test
        void shouldThrowExceptionWhenCategoryAlreadyExists() {

                User user = User.builder()
                        .id(UUID.randomUUID())
                        .build();

                CreateCategoryRequest request = new CreateCategoryRequest();
                request.setName("Mercado");
                request.setMovementType(MovementType.EXPENSE);

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(categoryRepository.existsByUserIdAndNameIgnoreCase(
                        user.getId(),
                        "Mercado"))
                        .thenReturn(true);

                assertThrows(
                        CategoryAlreadyExistsException.class,
                        () -> categoryService.create(request)
                );

                verify(categoryRepository, never()).save(any(Category.class));

        }

    @Test
        void shouldReturnAllCategories() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        Category defaultCategory = Category.builder()
                .name("Mercado")
                .movementType(MovementType.EXPENSE)
                .isDefault(true)
                .build();

        Category customCategory = Category.builder()
                .name("Mascotas")
                .movementType(MovementType.EXPENSE)
                .isDefault(false)
                .build();

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);

        when(categoryRepository.findByIsDefaultTrue())
                .thenReturn(List.of(defaultCategory));

        when(categoryRepository.findByUserId(user.getId()))
                .thenReturn(List.of(customCategory));

        when(categoryMapper.toResponseList(any()))
                .thenReturn(List.of(
                        CategoryResponse.builder().name("Mercado").build(),
                        CategoryResponse.builder().name("Mascotas").build()
                ));

        List<CategoryResponse> result = categoryService.findAll(null);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        }

}