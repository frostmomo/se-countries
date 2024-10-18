package com.test.country.entities;

import com.test.country.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsEntity {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handleUnexpectedExceptions(Exception ex) {
        String errorMessage = "An unexpected error occurred: " + ex.getMessage();
        ResponseDto<String> response = new ResponseDto<>(errorMessage, null, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseDto<String> response = new ResponseDto<>(ex.getMessage(), null, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
