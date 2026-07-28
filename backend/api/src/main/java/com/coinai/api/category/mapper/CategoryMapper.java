package com.coinai.api.category.mapper;

import com.coinai.api.category.dto.request.CreateCategoryRequest;
import com.coinai.api.category.dto.response.CategoryResponse;
import com.coinai.api.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Category toEntity(CreateCategoryRequest request);

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

}