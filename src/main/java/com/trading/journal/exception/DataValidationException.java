package com.trading.journal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataValidationException extends RuntimeException {

    private String message;

    public DataValidationException(String message) {
        super(message);
    }
}
