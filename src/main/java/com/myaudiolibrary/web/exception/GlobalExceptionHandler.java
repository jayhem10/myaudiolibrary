package com.myaudiolibrary.web.exception;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(
            EntityNotFoundException entityNotFoundException) {
        return entityNotFoundException.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityExistsException(
            EntityExistsException entityExistsException) {
        return entityExistsException.getMessage();
    }


    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalStateException(
            IllegalStateException illegalStateException) {
        return illegalStateException.getMessage();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return "Le paramètre " + e.getName() + " a une valeur incorrecte " + e.getValue();
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePropertyReferenceException(PropertyReferenceException p) {
        return "La propriété " + p.getPropertyName() + " demandée n'existe pas!";

    }

}
