package com.challenge.mule.controller;

import com.challenge.mule.exception.RestTemplateException;
import com.challenge.mule.model.dto.ExceptionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionsHandlerController extends ResponseEntityExceptionHandler{

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsHandlerController.class);

    @ExceptionHandler(RestTemplateException.class)
    ResponseEntity<ExceptionResponseDTO> restTemplateException(RestTemplateException e, HttpServletRequest request) {
        logger.error("Something was wrong " + e.getMessage(), e);
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ExceptionResponseDTO.ExceptionResponseDTOBuilder()
                    .setError(e.getMessage())
                    .setStatus(e.getStatusCode())
                    .setMessage(e.getMessage())
                    .setPath(request.getRequestURI())
                    .createErrorResponseDTO());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponseDTO> exception(Exception e, HttpServletRequest request) {
        logger.error("Something was wrong " + e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponseDTO.ExceptionResponseDTOBuilder()
                        .setError(e.getMessage())
                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .setMessage("this is so embarrass, something was wrong, please try again")
                        .setPath(request.getRequestURI())
                        .createErrorResponseDTO());
    }

}
