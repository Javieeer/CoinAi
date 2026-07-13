package com.coinai.api.budget.controller;

import com.coinai.api.budget.dto.request.CreateBudgetRequest;
import com.coinai.api.budget.dto.request.UpdateBudgetRequest;
import com.coinai.api.budget.dto.response.BudgetResponse;
import com.coinai.api.budget.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public BudgetResponse create(
            @Valid @RequestBody CreateBudgetRequest request
    ) {
        return budgetService.create(request);
    }

    @GetMapping
    public List<BudgetResponse> findAll(
            @RequestParam Short month,
            @RequestParam Short year
    ) {
        return budgetService.findAll(month, year);
    }

    @PutMapping("/{id}")
    public BudgetResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBudgetRequest request
    ) {
        return budgetService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable UUID id
    ) {
        budgetService.delete(id);
    }

}