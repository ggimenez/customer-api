package com.gabri.coding.customerapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super("Could not find resource " + resourceName + " with ID: " + id);
    }
}
