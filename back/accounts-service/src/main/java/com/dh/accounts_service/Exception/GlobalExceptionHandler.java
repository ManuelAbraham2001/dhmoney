package com.dh.accounts_service.Exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleValidationExceptions(AccountNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TransferDeniedException.class)
    public ResponseEntity<Object> handleTransferDeniedException(TransferDeniedException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AliasAlreadyExists.class)
    public ResponseEntity<String> handleValidationExceptions(AliasAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
