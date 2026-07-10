package com.coinai.api.common.exception;

public class CategoryModificationNotAllowedException extends RuntimeException {

    public CategoryModificationNotAllowedException() {
        super("Default categories cannot be modified.");
    }

}