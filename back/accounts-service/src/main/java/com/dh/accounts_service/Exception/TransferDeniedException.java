package com.dh.accounts_service.Exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.HttpStatus;

public class TransferDeniedException extends RuntimeException {
    private final HttpStatus status;

    public TransferDeniedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

