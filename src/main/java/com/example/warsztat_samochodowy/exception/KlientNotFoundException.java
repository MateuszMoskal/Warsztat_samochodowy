package com.example.warsztat_samochodowy.exception;

public class KlientNotFoundException extends RuntimeException {
    public KlientNotFoundException(String message) {
        super(message);
    }
}
