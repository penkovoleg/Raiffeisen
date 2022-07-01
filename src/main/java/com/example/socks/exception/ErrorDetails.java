package com.example.socks.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDetails {

    private Date timestamp;

    private int status;

    private String error;

    private String message;

    private String details;

}
