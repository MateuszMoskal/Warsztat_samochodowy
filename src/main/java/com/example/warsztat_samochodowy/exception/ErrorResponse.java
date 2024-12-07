package com.example.warsztat_samochodowy.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
        private String message;
        private HttpStatus status;

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
