package com.coinai.api.common.exception;

public class CategoryDeletionNotAllowedException extends RuntimeException {

    public CategoryDeletionNotAllowedException() {
        super("Default categories cannot be deleted.");
    }

}