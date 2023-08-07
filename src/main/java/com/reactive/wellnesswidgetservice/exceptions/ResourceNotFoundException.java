package com.reactive.wellnesswidgetservice.exceptions;

public class ResourceNotFoundException extends  RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
