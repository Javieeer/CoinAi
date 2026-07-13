package com.coinai.api.budget.mapper;

import com.coinai.api.budget.dto.request.CreateBudgetRequest;
import com.coinai.api.budget.dto.response.BudgetResponse;
import com.coinai.api.budget.entity.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    Budget toEntity(CreateBudgetRequest request);

    @Mapping(target = "familyId", source = "family.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "spent", ignore = true)
    @Mapping(target = "remaining", ignore = true)
    @Mapping(target = "percentage", ignore = true)
    BudgetResponse toResponse(Budget budget);

    List<BudgetResponse> toResponseList(List<Budget> budgets);

}