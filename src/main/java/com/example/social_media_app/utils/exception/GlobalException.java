package com.example.social_media_app.utils.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleUserNotFound(Exception ex ,WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setError(request.getDescription(false));// Get the error description from the request
        errorDetails.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);

    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFound(UserException ex ,WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setError(request.getDescription(false));// Get the error description from the request
        errorDetails.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);

    }


}
