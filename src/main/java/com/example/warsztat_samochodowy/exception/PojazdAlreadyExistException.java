package com.example.warsztat_samochodowy.exception;

public class PojazdAlreadyExistException extends RuntimeException {
    public PojazdAlreadyExistException(String message) {
        super(message);
    }
}
