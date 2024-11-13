package com.example.warsztat_samochodowy.exception;

public class KlientAlreadyExistException extends RuntimeException {
    public KlientAlreadyExistException(String message) {
        super(message);
    }
}
