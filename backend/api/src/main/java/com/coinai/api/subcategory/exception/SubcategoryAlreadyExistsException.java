package com.coinai.api.subcategory.exception;

public class SubcategoryAlreadyExistsException extends RuntimeException {

    public SubcategoryAlreadyExistsException() {
        super("Subcategory already exists.");
    }

}