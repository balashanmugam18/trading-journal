package com.trading.journal.exception;

import com.trading.journal.model.ErrorResponse;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    List<ErrorResponse> errorResponseList = new ArrayList<>();

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleMethodArgumentException(MethodArgumentNotValidException exception, WebRequest request) {

        errorResponseList = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            // New ErrorResponse for each field
            errorResponseList.add(ErrorResponse.builder()
                    .parameter(error.getField())
                    .problem(error.getDefaultMessage())
                    .valueSubmitted(error.getRejectedValue() != null ? String.valueOf(error.getRejectedValue()) : "null").build());
        });

        log.warn("Validation failed: {} errors", errorResponseList.size());
        return ResponseEntity.badRequest().body(errorResponseList);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<?> handleUnexpectedTypeException(UnexpectedTypeException exception, WebRequest request) {
        ErrorResponse error = ErrorResponse.builder().parameter("validation_config")
                .problem("Validator configuration error: " + exception.getMessage()+" Field Mapping error.")
                .valueSubmitted("type_mismatch").build();

        log.error("Validator type mismatch: {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }

}
