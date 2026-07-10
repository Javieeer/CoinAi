package com.coinai.api.category.service.impl;

import com.coinai.api.category.dto.request.CreateCategoryRequest;
import com.coinai.api.category.dto.request.UpdateCategoryRequest;
import com.coinai.api.category.dto.response.CategoryResponse;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.mapper.CategoryMapper;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.common.exception.CategoryAlreadyExistsException;
import com.coinai.api.common.exception.CategoryDeletionNotAllowedException;
import com.coinai.api.common.exception.CategoryModificationNotAllowedException;
import com.coinai.api.common.exception.CategoryNotFoundException;
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
import static org.mockito.ArgumentMatchers.eq;
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

    @Test
        void shouldUpdateCategorySuccessfully() {

                UUID userId = UUID.randomUUID();
                UUID categoryId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                Category category = Category.builder()
                        .id(categoryId)
                        .name("Mercado")
                        .movementType(MovementType.EXPENSE)
                        .isDefault(false)
                        .user(user)
                        .build();

                UpdateCategoryRequest request = new UpdateCategoryRequest();
                request.setName("Supermercado");
                request.setIcon("cart");
                request.setColor("#4CAF50");
                request.setMovementType(MovementType.EXPENSE);

                CategoryResponse response = CategoryResponse.builder()
                        .id(categoryId)
                        .name("Supermercado")
                        .movementType(MovementType.EXPENSE)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);
                when(categoryRepository.findByIdAndUserId(categoryId, userId))
                        .thenReturn(java.util.Optional.of(category));
                when(categoryRepository.existsByUserIdAndNameIgnoreCase(userId, "Supermercado"))
                        .thenReturn(false);
                when(categoryRepository.save(any(Category.class)))
                        .thenReturn(category);
                when(categoryMapper.toResponse(category))
                        .thenReturn(response);

                CategoryResponse result = categoryService.update(categoryId, request);

                assertEquals("Supermercado", result.getName());

        }

    @Test
        void shouldThrowWhenUpdatingNonExistingCategory() {

                UUID userId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(categoryRepository.findByIdAndUserId(any(), eq(userId)))
                        .thenReturn(java.util.Optional.empty());

                assertThrows(
                        CategoryNotFoundException.class,
                        () -> categoryService.update(UUID.randomUUID(), new UpdateCategoryRequest())
                );

        }

    @Test
        void shouldNotUpdateDefaultCategory() {

                UUID userId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                Category category = Category.builder()
                        .isDefault(true)
                        .user(user)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(categoryRepository.findByIdAndUserId(any(), eq(userId)))
                        .thenReturn(java.util.Optional.of(category));

                assertThrows(
                        CategoryModificationNotAllowedException.class,
                        () -> categoryService.update(UUID.randomUUID(), new UpdateCategoryRequest())
                );

        }

    @Test
        void shouldDeleteCategorySuccessfully() {

                UUID userId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                Category category = Category.builder()
                        .isDefault(false)
                        .user(user)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(categoryRepository.findByIdAndUserId(any(), eq(userId)))
                        .thenReturn(java.util.Optional.of(category));

                categoryService.delete(UUID.randomUUID());

                verify(categoryRepository).delete(category);

        }

    @Test
        void shouldNotDeleteDefaultCategory() {

                UUID userId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                Category category = Category.builder()
                        .isDefault(true)
                        .user(user)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(categoryRepository.findByIdAndUserId(any(), eq(userId)))
                        .thenReturn(java.util.Optional.of(category));

                assertThrows(
                        CategoryDeletionNotAllowedException.class,
                        () -> categoryService.delete(UUID.randomUUID())
                );

                verify(categoryRepository, never()).delete(any());

        }

    @Test
        void shouldThrowWhenDeletingNonExistingCategory() {

                UUID userId = UUID.randomUUID();

                User user = User.builder()
                        .id(userId)
                        .build();

                when(authenticatedUserService.getCurrentUser()).thenReturn(user);

                when(categoryRepository.findByIdAndUserId(any(), eq(userId)))
                        .thenReturn(java.util.Optional.empty());

                assertThrows(
                        CategoryNotFoundException.class,
                        () -> categoryService.delete(UUID.randomUUID())
                );

        }


        
}