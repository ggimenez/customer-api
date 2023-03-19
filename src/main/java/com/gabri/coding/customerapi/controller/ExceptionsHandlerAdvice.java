package com.gabri.coding.customerapi.controller;


import com.gabri.coding.customerapi.exceptions.ResourceNotFoundException;
import com.gabri.coding.customerapi.exceptions.ServiceLayerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ExceptionsHandlerAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String resourceNotFoundHandler(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ServiceLayerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String serviceExceptionHandler(ServiceLayerException ex) {
        return ex.getMessage();
    }
}