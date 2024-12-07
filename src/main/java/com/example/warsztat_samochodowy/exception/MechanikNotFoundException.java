package com.example.warsztat_samochodowy.exception;

public class MechanikNotFoundException extends RuntimeException {
    public MechanikNotFoundException(String message) {
        super(message);
    }
}
