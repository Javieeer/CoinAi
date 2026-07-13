package com.coinai.api.subcategory.service.impl;

import com.coinai.api.category.entity.Category;
import com.coinai.api.category.exception.CategoryNotFoundException;
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
import com.coinai.api.subcategory.service.SubcategoryService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryMapper subcategoryMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public SubcategoryResponse create(CreateSubcategoryRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (subcategoryRepository.existsByUserIdAndCategoryIdAndNameIgnoreCase(
                user.getId(),
                request.getCategoryId(),
                request.getName()
        )) {
            throw new SubcategoryAlreadyExistsException();
        }

        Category category = categoryRepository.findByIdAndUserId(
                request.getCategoryId(),
                user.getId()
        ).orElseThrow(CategoryNotFoundException::new);

        Subcategory subcategory = subcategoryMapper.toEntity(request);

        subcategory.setUser(user);
        subcategory.setCategory(category);
        subcategory.setDefault(false);
        subcategory.setArchived(false);
        subcategory.setCreatedAt(LocalDateTime.now());
        subcategory.setUpdatedAt(LocalDateTime.now());

        subcategory = subcategoryRepository.save(subcategory);

        return subcategoryMapper.toResponse(subcategory);

    }

    @Override
    public List<SubcategoryResponse> findAll() {

        User user = authenticatedUserService.getCurrentUser();

        return subcategoryMapper.toResponseList(
                subcategoryRepository.findByUserIdAndArchivedFalseOrderByNameAsc(
                        user.getId()
                )
        );

    }

    @Override
    public SubcategoryResponse update(
            UUID id,
            UpdateSubcategoryRequest request
    ) {

        User user = authenticatedUserService.getCurrentUser();

        Subcategory subcategory = subcategoryRepository.findByIdAndUserId(
                id,
                user.getId()
        ).orElseThrow(SubcategoryNotFoundException::new);

        Category category = categoryRepository.findByIdAndUserId(
                request.getCategoryId(),
                user.getId()
        ).orElseThrow(CategoryNotFoundException::new);

        if (
                (!subcategory.getName().equalsIgnoreCase(request.getName())
                || !subcategory.getCategory().getId().equals(request.getCategoryId()))
                &&
                subcategoryRepository.existsByUserIdAndCategoryIdAndNameIgnoreCase(
                        user.getId(),
                        request.getCategoryId(),
                        request.getName()
                )
        ) {
            throw new SubcategoryAlreadyExistsException();
        }

        subcategory.setCategory(category);
        subcategory.setName(request.getName());
        subcategory.setIcon(request.getIcon());
        subcategory.setColor(request.getColor());
        subcategory.setUpdatedAt(LocalDateTime.now());

        subcategory = subcategoryRepository.save(subcategory);

        return subcategoryMapper.toResponse(subcategory);

    }

    @Override
    public void delete(UUID id) {

        User user = authenticatedUserService.getCurrentUser();

        Subcategory subcategory = subcategoryRepository.findByIdAndUserId(
                id,
                user.getId()
        ).orElseThrow(SubcategoryNotFoundException::new);

        subcategory.setArchived(true);
        subcategory.setUpdatedAt(LocalDateTime.now());

        subcategoryRepository.save(subcategory);

    }

}