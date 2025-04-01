package com.dh.users_service.Exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(UUID id) {
        super("No existe un usuario con el id: " + id);
    }
}
