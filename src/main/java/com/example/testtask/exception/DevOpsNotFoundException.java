package com.example.testtask.exception;

public class DevOpsNotFoundException extends RuntimeException {
    public DevOpsNotFoundException(long id) {
        super("The devOps with id %d was not found".formatted(id));
    }
}
