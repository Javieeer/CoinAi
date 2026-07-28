package com.coinai.api.subcategory.service.impl;

import com.coinai.api.subcategory.service.DefaultSubcategoryService;
import com.coinai.api.user.entity.User;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.enums.CategoryOrigin;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.subcategory.entity.Subcategory;
import com.coinai.api.subcategory.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultSubcategoryServiceImpl
        implements DefaultSubcategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    private static final Map<String, List<String>> DEFAULT_SUBCATEGORIES =
        new LinkedHashMap<>();
    
    static {

        DEFAULT_SUBCATEGORIES.put(
                "Alimentación",
                List.of(
                        "Supermercado",
                        "Restaurantes",
                        "Café",
                        "Domicilios"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Transporte",
                List.of(
                        "Gasolina",
                        "Taxi",
                        "Transporte público",
                        "Parqueadero"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Hogar",
                List.of(
                        "Arriendo",
                        "Servicios públicos",
                        "Internet",
                        "Mantenimiento"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Salud",
                List.of(
                        "Medicamentos",
                        "Consultas",
                        "Exámenes"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Educación",
                List.of(
                        "Universidad",
                        "Cursos",
                        "Libros"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Entretenimiento",
                List.of(
                        "Cine",
                        "Streaming",
                        "Viajes"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Compras",
                List.of(
                        "Ropa",
                        "Tecnología",
                        "Accesorios"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Servicios",
                List.of(
                        "Telefonía",
                        "Internet",
                        "Suscripciones"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Mascotas",
                List.of(
                        "Comida",
                        "Veterinario",
                        "Accesorios"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Salario",
                List.of(
                        "Sueldo",
                        "Prima",
                        "Bonificaciones"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Freelance",
                List.of(
                        "Desarrollo",
                        "Diseño",
                        "Consultoría"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Inversiones",
                List.of(
                        "Dividendos",
                        "Intereses",
                        "Criptomonedas"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Regalos",
                List.of(
                        "Familia",
                        "Amigos"
                )
        );

        DEFAULT_SUBCATEGORIES.put(
                "Otros",
                List.of(
                        "Otros ingresos"
                )
        );

    }

    @Override
    public void createDefaults(User user) {

        for (Map.Entry<String, List<String>> entry : DEFAULT_SUBCATEGORIES.entrySet()) {

            Category category =
                    categoryRepository
                            .findByUserIdAndNameIgnoreCase(
                                    user.getId(),
                                    entry.getKey()
                            )
                            .orElse(null);

            if (category == null) {
                continue;
            }

            for (String subcategoryName : entry.getValue()) {

                Subcategory subcategory =
                        Subcategory.builder()
                                .user(user)
                                .category(category)
                                .name(subcategoryName)
                                .origin(CategoryOrigin.SYSTEM)
                                .createdAt(LocalDateTime.now())
                                .build();

                boolean exists =
                        subcategoryRepository
                                .existsByUserIdAndCategoryIdAndNameIgnoreCase(
                                        user.getId(),
                                        category.getId(),
                                        subcategoryName
                                );

                if (!exists) {

                    subcategoryRepository.save(subcategory);

                }

            }

        }

    }

}