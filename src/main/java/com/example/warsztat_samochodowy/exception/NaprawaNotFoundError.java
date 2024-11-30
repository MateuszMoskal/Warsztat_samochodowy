package com.example.warsztat_samochodowy.exception;

public class NaprawaNotFoundError extends RuntimeException {
    public NaprawaNotFoundError(String message) {
        super(message);
    }
}
