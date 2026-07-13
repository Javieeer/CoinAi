package com.coinai.api.budget.service;

import com.coinai.api.budget.dto.request.CreateBudgetRequest;
import com.coinai.api.budget.dto.request.UpdateBudgetRequest;
import com.coinai.api.budget.dto.response.BudgetResponse;

import java.util.List;
import java.util.UUID;

public interface BudgetService {

    BudgetResponse create(CreateBudgetRequest request);

    List<BudgetResponse> findAll(
            Short month,
            Short year
    );

    BudgetResponse update(
            UUID id,
            UpdateBudgetRequest request
    );

    void delete(UUID id);

}