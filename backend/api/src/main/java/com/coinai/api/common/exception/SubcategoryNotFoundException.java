package com.coinai.api.common.exception;

public class SubcategoryNotFoundException extends RuntimeException {

    public SubcategoryNotFoundException() {
        super("Subcategory not found.");
    }
    
}
