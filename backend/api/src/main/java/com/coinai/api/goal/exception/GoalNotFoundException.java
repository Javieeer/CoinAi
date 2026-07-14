package com.coinai.api.goal.exception;

public class GoalNotFoundException extends RuntimeException {

    public GoalNotFoundException() {
        super("Goal not found.");
    }

}