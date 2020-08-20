package com.challenge.mule.exception;

public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException(String message) {
        this.message = message;
    }
}
