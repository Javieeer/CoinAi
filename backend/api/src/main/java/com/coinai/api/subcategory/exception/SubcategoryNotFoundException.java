package com.coinai.api.subcategory.exception;

public class SubcategoryNotFoundException extends RuntimeException {

    public SubcategoryNotFoundException() {
        super("Subcategory not found.");
    }
    
}
