package com.coinai.api.subcategory.service.impl;

import com.coinai.api.category.entity.Category;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.subcategory.dto.request.CreateSubcategoryRequest;
import com.coinai.api.subcategory.dto.request.UpdateSubcategoryRequest;
import com.coinai.api.subcategory.dto.response.SubcategoryResponse;
import com.coinai.api.subcategory.entity.Subcategory;
import com.coinai.api.subcategory.exception.SubcategoryAlreadyExistsException;
import com.coinai.api.subcategory.exception.SubcategoryNotFoundException;
import com.coinai.api.subcategory.mapper.SubcategoryMapper;
import com.coinai.api.subcategory.repository.SubcategoryRepository;
import com.coinai.api.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubcategoryServiceImplTest {

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SubcategoryMapper subcategoryMapper;

    @Mock
    private AuthenticatedUserService authenticatedUserService;

    @InjectMocks
    private SubcategoryServiceImpl subcategoryService;

    @Test
    void shouldCreateSubcategorySuccessfully() {

        // Arrange

        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Category category = Category.builder()
                .id(categoryId)
                .name("Food")
                .user(user)
                .build();

        CreateSubcategoryRequest request = new CreateSubcategoryRequest();
        request.setCategoryId(categoryId);
        request.setName("Fast Food");
        request.setIcon("burger");
        request.setColor("#FF0000");

        Subcategory subcategory = Subcategory.builder()
                .name("Fast Food")
                .icon("burger")
                .color("#FF0000")
                .user(user)
                .category(category)
                .build();

        SubcategoryResponse response = SubcategoryResponse.builder()
                .id(UUID.randomUUID())
                .categoryId(categoryId)
                .name("Fast Food")
                .icon("burger")
                .color("#FF0000")
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(subcategoryRepository.existsByUserIdAndCategoryIdAndNameIgnoreCase(
                userId,
                categoryId,
                "Fast Food"))
                .thenReturn(false);

        when(categoryRepository.findByIdAndUserId(categoryId, userId))
                .thenReturn(Optional.of(category));

        when(subcategoryMapper.toEntity(request))
                .thenReturn(subcategory);

        when(subcategoryRepository.save(any(Subcategory.class)))
                .thenReturn(subcategory);

        when(subcategoryMapper.toResponse(subcategory))
                .thenReturn(response);

        // Act

        SubcategoryResponse result = subcategoryService.create(request);

        // Assert

        assertEquals("Fast Food", result.getName());
        assertEquals(categoryId, result.getCategoryId());
        assertEquals("burger", result.getIcon());
        assertEquals("#FF0000", result.getColor());

    }

    @Test
    void shouldThrowExceptionWhenSubcategoryAlreadyExists() {

        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        CreateSubcategoryRequest request = new CreateSubcategoryRequest();
        request.setCategoryId(categoryId);
        request.setName("Fast Food");

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(subcategoryRepository.existsByUserIdAndCategoryIdAndNameIgnoreCase(
                userId,
                categoryId,
                "Fast Food"))
                .thenReturn(true);

        assertThrows(
                SubcategoryAlreadyExistsException.class,
                () -> subcategoryService.create(request)
        );

        verify(subcategoryRepository, never()).save(any());

    }

    @Test
    void shouldReturnAllSubcategories() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        List<Subcategory> subcategories = List.of(
                Subcategory.builder()
                        .name("Fast Food")
                        .build(),
                Subcategory.builder()
                        .name("Restaurants")
                        .build()
        );

        List<SubcategoryResponse> responses = List.of(
                SubcategoryResponse.builder()
                        .name("Fast Food")
                        .build(),
                SubcategoryResponse.builder()
                        .name("Restaurants")
                        .build()
        );

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(subcategoryRepository.findByUserIdAndArchivedFalseOrderByNameAsc(userId))
                .thenReturn(subcategories);

        when(subcategoryMapper.toResponseList(subcategories))
                .thenReturn(responses);

        List<SubcategoryResponse> result = subcategoryService.findAll();

        assertEquals(2, result.size());
        assertEquals("Fast Food", result.get(0).getName());
        assertEquals("Restaurants", result.get(1).getName());

    }

    @Test
    void shouldUpdateSubcategorySuccessfully() {

        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID subcategoryId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Category category = Category.builder()
                .id(categoryId)
                .user(user)
                .build();

        Subcategory subcategory = Subcategory.builder()
                .id(subcategoryId)
                .name("Fast Food")
                .icon("burger")
                .color("#000000")
                .user(user)
                .category(category)
                .build();

        UpdateSubcategoryRequest request = new UpdateSubcategoryRequest();
        request.setCategoryId(categoryId);
        request.setName("Restaurants");
        request.setIcon("restaurant");
        request.setColor("#FFFFFF");

        SubcategoryResponse response = SubcategoryResponse.builder()
                .id(subcategoryId)
                .categoryId(categoryId)
                .name("Restaurants")
                .icon("restaurant")
                .color("#FFFFFF")
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(subcategoryRepository.findByIdAndUserId(subcategoryId, userId))
                .thenReturn(Optional.of(subcategory));

        when(categoryRepository.findByIdAndUserId(categoryId, userId))
                .thenReturn(Optional.of(category));

        when(subcategoryRepository.existsByUserIdAndCategoryIdAndNameIgnoreCase(
                userId,
                categoryId,
                "Restaurants"))
                .thenReturn(false);

        when(subcategoryRepository.save(subcategory))
                .thenReturn(subcategory);

        when(subcategoryMapper.toResponse(subcategory))
                .thenReturn(response);

        SubcategoryResponse result =
                subcategoryService.update(subcategoryId, request);

        assertEquals("Restaurants", result.getName());
        assertEquals("restaurant", result.getIcon());
        assertEquals("#FFFFFF", result.getColor());

    }

    @Test
    void shouldThrowWhenUpdatingNonExistingSubcategory() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(subcategoryRepository.findByIdAndUserId(any(), eq(userId)))
                .thenReturn(Optional.empty());

        assertThrows(
                SubcategoryNotFoundException.class,
                () -> subcategoryService.update(
                        UUID.randomUUID(),
                        new UpdateSubcategoryRequest()
                )
        );

    }

    @Test
    void shouldArchiveSubcategorySuccessfully() {

        UUID userId = UUID.randomUUID();
        UUID subcategoryId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        Subcategory subcategory = Subcategory.builder()
                .id(subcategoryId)
                .archived(false)
                .user(user)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(subcategoryRepository.findByIdAndUserId(
                subcategoryId,
                userId
        )).thenReturn(Optional.of(subcategory));

        subcategoryService.delete(subcategoryId);

        assertTrue(subcategory.isArchived());

        verify(subcategoryRepository).save(subcategory);

    }

    @Test
    void shouldThrowWhenDeletingNonExistingSubcategory() {

        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        when(authenticatedUserService.getCurrentUser())
                .thenReturn(user);

        when(subcategoryRepository.findByIdAndUserId(any(), eq(userId)))
                .thenReturn(Optional.empty());

        assertThrows(
                SubcategoryNotFoundException.class,
                () -> subcategoryService.delete(UUID.randomUUID())
        );

    }
}
