package com.example.socks.exception;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetails {

    private Date timestamp;

    private int status;

    private String details;

    private String message;

    private String error;

}
