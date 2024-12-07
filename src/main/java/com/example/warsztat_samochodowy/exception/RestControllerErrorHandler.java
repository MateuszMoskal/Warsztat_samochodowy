package com.example.warsztat_samochodowy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestControllerErrorHandler {

    @ExceptionHandler(KlientAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse klientAlreadyExist(KlientAlreadyExistException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(KlientNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse klientNotFound(KlientNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MechanikAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse mechanikAlreadyExist(MechanikAlreadyExistException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MechanikNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse mechanikNotFound(MechanikNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NaprawaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse naprawaNotFound(NaprawaNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PojazdAlreadyExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse pojazdAlreadyExist(PojazdAlreadyExistException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PojazdNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse pojazdNotFound(PojazdNotFoundException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataToEarlyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse dataToEarly(DataToEarlyException exception) {
        String message = exception.getMessage();
        return new ErrorResponse(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
