package com.coinai.api.category.exception;

public class CategoryDeletionNotAllowedException extends RuntimeException {

    public CategoryDeletionNotAllowedException() {
        super("Default categories cannot be deleted.");
    }

}