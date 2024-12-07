package com.example.warsztat_samochodowy.exception;

public class MechanikAlreadyExistException extends RuntimeException {
    public MechanikAlreadyExistException(String message) {
        super(message);
    }
}
