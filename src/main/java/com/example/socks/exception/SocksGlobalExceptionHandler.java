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

//    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
//    public ResponseEntity<ErrorDetails> handlerValidationException(Exception exception) {
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//        String message = null;
//        if (exception.getCause() instanceof MethodArgumentNotValidException) {
//            MethodArgumentNotValidException methodArgumentNotValidException =
//                    (MethodArgumentNotValidException) exception.getCause();
//            message = methodArgumentNotValidException
//                    .getBindingResult()
//                    .getAllErrors()
//                    .get(0)
//                    .getDefaultMessage();
//        } else if (exception.getCause() instanceof ConstraintViolationException){
//            ConstraintViolationException constraintViolationException =
//                    (ConstraintViolationException) exception.getCause();
//            message = constraintViolationException.getConstraintViolations()
//                    .stream()
//                    .map(ConstraintViolation::getMessage)
//                    .collect(Collectors.toList())
//                    .get(0);
//        }
//        ErrorDetails data = new ErrorDetails(new Date(),
//                httpStatus.value(),
//                exception
//                        .getClass()
//                        .getSimpleName() + ": Параметры запроса отсутствуют или имеют некорректный формат!",
//                message,
//                httpStatus.getReasonPhrase());
//        return new ResponseEntity<>(data, httpStatus);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDetails data = new ErrorDetails(
                new Date(),
                httpStatus.value(),
                exception.getClass()
                        .getSimpleName()
                        + ": Параметры запроса отсутствуют или имеют некорректный формат!",
                exception.getMessage(),
                httpStatus.getReasonPhrase());
        return new ResponseEntity<>(data, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handlerConstraintViolationException(ConstraintViolationException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDetails data = new ErrorDetails(
                new Date(),
                httpStatus.value(),
                exception.getClass()
                        .getSimpleName()
                        + ": Параметры запроса отсутствуют или имеют некорректный формат!",
                exception.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList())
                        .get(0),
                httpStatus.getReasonPhrase());
        return new ResponseEntity<>(data, httpStatus);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDetails> handlerNoSuchElementException(NoSuchElementException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorDetails data = new ErrorDetails(
                new Date(),
                httpStatus.value(),
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                httpStatus.getReasonPhrase());
        return new ResponseEntity<>(data, httpStatus);
    }
}
