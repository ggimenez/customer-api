package com.gabri.coding.customerapi.exceptions;

public class ServiceLayerException extends RuntimeException {

    private String message;

    public ServiceLayerException() {}

    public ServiceLayerException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
