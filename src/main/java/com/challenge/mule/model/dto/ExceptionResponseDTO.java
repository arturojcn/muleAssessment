package com.challenge.mule.model.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ExceptionResponseDTO {
    private String timestamp;
    private HttpStatus status;
    private String error;
    private String message;
    private String path;

    public ExceptionResponseDTO() {}

    private ExceptionResponseDTO(HttpStatus status, String error, String message, String path) {
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }


    public static class ExceptionResponseDTOBuilder {
        private HttpStatus status;
        private String error;
        private String message;
        private String path;

        public ExceptionResponseDTOBuilder setStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ExceptionResponseDTOBuilder setError(String error) {
            this.error = error;
            return this;
        }

        public ExceptionResponseDTOBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ExceptionResponseDTOBuilder setPath(String path) {
            this.path = path;
            return this;
        }

        public ExceptionResponseDTO createErrorResponseDTO() {
            return new ExceptionResponseDTO(status, error, message, path);
        }
    }



}
