package com.dh.keycloak_service.Exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String email) {
        super("Ya existe un usuario con el email: " + email);
    }
}
