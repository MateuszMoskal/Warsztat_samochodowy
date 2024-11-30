package com.example.warsztat_samochodowy.exception;

public class KlientNotFoundError extends RuntimeException {
    public KlientNotFoundError(String message) {
        super(message);
    }
}
