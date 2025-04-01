package com.dh.cards_service.Exception;

import java.util.UUID;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException(UUID id) {
        super("No existe una tarjeta con el id: " + id);
    }
}
