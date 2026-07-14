package com.coinai.api.goal.exception;

public class GoalAlreadyExistsException extends RuntimeException {

    public GoalAlreadyExistsException() {
        super("A goal with the same name already exists.");
    }

}