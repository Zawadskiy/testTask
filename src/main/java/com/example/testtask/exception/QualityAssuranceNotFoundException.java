package com.example.testtask.exception;

public class QualityAssuranceNotFoundException extends RuntimeException {
    public QualityAssuranceNotFoundException(long id) {
        super("The qualityAssurance with id %d was not found".formatted(id));
    }
}
