package com.dh.activities_service.Exception;

import java.util.UUID;

public class ActivityNotFoundException extends RuntimeException{
    public ActivityNotFoundException(UUID id) {
        super("No existe una transaccion con el id: " + id);
    }
}
