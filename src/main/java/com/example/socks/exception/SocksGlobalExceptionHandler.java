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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        ErrorDetails details = createResponse(exception);
        details.setDetails(exception
                .getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorDetails> handlerIllegalArgumentAndNoSuchElementException(RuntimeException exception) {
        ErrorDetails details = createResponse(exception);
        details.setDetails(exception.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handlerConstraintViolationException(
            ConstraintViolationException exception) {
        ErrorDetails details = createResponse(exception);
        details.setDetails(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .get(0));
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    private ErrorDetails createResponse(Exception exception) {
        return ErrorDetails.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getClass().getSimpleName()
                        + ": Параметры запроса отсутствуют или имеют некорректный формат!")
                .build();
    }
}
