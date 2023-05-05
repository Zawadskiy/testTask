package com.example.testtask.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(long id) {
        super("The project with id %d was not found".formatted(id));
    }
}
