package com.coinai.api.budget.exception;

public class BudgetNotFoundException extends RuntimeException {

    public BudgetNotFoundException() {
        super("Budget not found.");
    }

}