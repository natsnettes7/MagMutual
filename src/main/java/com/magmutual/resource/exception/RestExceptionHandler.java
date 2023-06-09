package com.magmutual.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse;
        errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "RESOURCE_NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadException(InvalidRequestException ex) {
        ErrorResponse errorResponse;
        errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "RESOURCE_NOT_FOUND");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}