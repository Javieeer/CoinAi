package com.coinai.api.subcategory.mapper;

import com.coinai.api.subcategory.dto.request.CreateSubcategoryRequest;
import com.coinai.api.subcategory.dto.response.SubcategoryResponse;
import com.coinai.api.subcategory.entity.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "isDefault", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Subcategory toEntity(CreateSubcategoryRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "isDefault", source = "default")
    SubcategoryResponse toResponse(Subcategory subcategory);

    List<SubcategoryResponse> toResponseList(
            List<Subcategory> subcategories
    );

}