package com.challenge.mule.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException(String message) {
        this.message = message;
    }
}
