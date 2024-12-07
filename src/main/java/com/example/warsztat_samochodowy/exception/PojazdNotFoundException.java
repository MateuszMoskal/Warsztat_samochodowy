package com.example.warsztat_samochodowy.exception;

public class PojazdNotFoundException extends RuntimeException {
    public PojazdNotFoundException(String message) {
        super(message);
    }
}
