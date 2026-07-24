package com.coinai.api.tag.exception;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException() {
        super("Tag not found.");
    }

}