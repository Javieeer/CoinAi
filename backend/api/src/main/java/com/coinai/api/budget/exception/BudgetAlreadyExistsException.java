package com.coinai.api.budget.exception;

public class BudgetAlreadyExistsException extends RuntimeException {

    public BudgetAlreadyExistsException() {
        super("A budget already exists for this category and period.");
    }

}