package com.coinai.api.common.exception;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException() {
        super("Ya existe una categoría con ese nombre.");
    }

}