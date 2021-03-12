package com.revature.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorModel> handleEntityNotFound(EntityNotFoundException ex){
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, "Entity not found", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    private ResponseEntity<ErrorModel> handleEntityExist(EntityExistsException ex){
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Entity exist", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    private ResponseEntity<ErrorModel> methodNotAllowed(MethodNotAllowedException ex){
        ErrorModel error = new ErrorModel(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }
}