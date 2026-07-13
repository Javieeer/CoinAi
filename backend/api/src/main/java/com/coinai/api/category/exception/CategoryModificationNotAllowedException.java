package com.coinai.api.category.exception;

public class CategoryModificationNotAllowedException extends RuntimeException {

    public CategoryModificationNotAllowedException() {
        super("Default categories cannot be modified.");
    }

}