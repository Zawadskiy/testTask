package com.example.testtask.exception;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(long id) {
        super("The developer with id %d was not found".formatted(id));
    }
}
