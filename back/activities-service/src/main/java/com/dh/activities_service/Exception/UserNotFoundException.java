package com.dh.activities_service.Exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(UUID id){
        super("Usuario no encontrado con el id: " + id);
    }
}
