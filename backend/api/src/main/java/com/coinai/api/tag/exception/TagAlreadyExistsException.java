package com.coinai.api.tag.exception;

public class TagAlreadyExistsException extends RuntimeException {

    public TagAlreadyExistsException() {

        super("A tag with this name already exists.");

    }

}