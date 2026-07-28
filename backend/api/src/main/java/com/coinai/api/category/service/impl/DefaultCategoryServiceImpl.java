package com.coinai.api.category.service.impl;

import com.coinai.api.category.entity.Category;
import com.coinai.api.category.enums.CategoryOrigin;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.category.service.DefaultCategoryService;
import com.coinai.api.movement.MovementType;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCategoryServiceImpl
        implements DefaultCategoryService {

    private final CategoryRepository categoryRepository;

    private static final List<String> EXPENSE_CATEGORIES = List.of(
            "Alimentación",
            "Hogar",
            "Transporte",
            "Salud",
            "Educación",
            "Entretenimiento",
            "Compras",
            "Servicios",
            "Mascotas",
            "Otros"
    );

    private static final List<String> INCOME_CATEGORIES = List.of(
            "Salario",
            "Freelance",
            "Inversiones",
            "Regalos",
            "Intereses",
            "Otros"
    );

    @Override
    public void createDefaults(User user) {

        LocalDateTime now = LocalDateTime.now();

        EXPENSE_CATEGORIES.forEach(name ->

                categoryRepository.save(
                        Category.builder()
                                .user(user)
                                .name(name)
                                .movementType(MovementType.EXPENSE)
                                .origin(CategoryOrigin.SYSTEM)
                                .createdAt(now)
                                .build()
                )
        );

        INCOME_CATEGORIES.forEach(name ->

                categoryRepository.save(
                        Category.builder()
                                .user(user)
                                .name(name)
                                .movementType(MovementType.INCOME)
                                .origin(CategoryOrigin.SYSTEM)
                                .createdAt(now)
                                .build()
                )
        );

    }

}