package com.example.socks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class SocksGlobalExceptionHandler {

    private static final String ARGUMENT_NOT_VALID = ": Параметры запроса отсутствуют или имеют некорректный формат!";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDetails details = createResponse(exception, httpStatus);
        details.setDetails(exception
                .getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage());
        details.setMessage(details.getMessage() + ARGUMENT_NOT_VALID);
        return new ResponseEntity<>(details, httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handlerIllegalArgumentException(RuntimeException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDetails details = createResponse(exception, httpStatus);
        details.setDetails(exception.getMessage());
        details.setMessage(details.getMessage() + ARGUMENT_NOT_VALID);
        return new ResponseEntity<>(details, httpStatus);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDetails> handlerNoSuchElementException(RuntimeException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorDetails details = createResponse(exception, httpStatus);
        details.setDetails(exception.getMessage());
        return new ResponseEntity<>(details, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handlerConstraintViolationException(
            ConstraintViolationException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDetails details = createResponse(exception, httpStatus);
        details.setDetails(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .get(0));
        details.setMessage(details.getMessage() + ARGUMENT_NOT_VALID);
        return new ResponseEntity<>(details, httpStatus);
    }

    private ErrorDetails createResponse(Exception exception, HttpStatus httpStatus) {
        return ErrorDetails.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(exception.getClass().getSimpleName())
                .build();
    }
}
