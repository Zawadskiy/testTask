package com.example.testtask.controller;

import com.example.testtask.dto.ErrorDto;
import com.example.testtask.exception.DevOpsNotFoundException;
import com.example.testtask.exception.DeveloperNotFoundException;
import com.example.testtask.exception.ProjectNotFoundException;
import com.example.testtask.exception.QualityAssuranceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeveloperNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorDto developerNotFound(DeveloperNotFoundException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ExceptionHandler(DevOpsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorDto devOpsNotFound(DevOpsNotFoundException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorDto projectNotFound(ProjectNotFoundException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ExceptionHandler(QualityAssuranceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorDto qualityAssuranceNotFound(QualityAssuranceNotFoundException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDto illegalArgument(IllegalArgumentException exception) {
        return new ErrorDto(exception.getMessage());
    }
}
