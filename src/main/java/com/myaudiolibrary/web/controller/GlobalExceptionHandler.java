package com.myaudiolibrary.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(
                IllegalStateException illegalStateException) {
            return illegalStateException.getMessage();
        }

}
