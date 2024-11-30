package com.example.warsztat_samochodowy.exception;

public class MechanikNotFoundError extends RuntimeException {
    public MechanikNotFoundError(String message) {
        super(message);
    }
}
