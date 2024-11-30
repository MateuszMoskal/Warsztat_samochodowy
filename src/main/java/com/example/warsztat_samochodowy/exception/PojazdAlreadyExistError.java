package com.example.warsztat_samochodowy.exception;

public class PojazdAlreadyExistError extends RuntimeException {
    public PojazdAlreadyExistError(String message) {
        super(message);
    }
}
