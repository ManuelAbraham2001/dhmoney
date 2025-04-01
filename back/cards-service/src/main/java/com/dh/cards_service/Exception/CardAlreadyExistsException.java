package com.dh.cards_service.Exception;

public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException(String number) {
        super("Ya existe una tarjeta con el número: " + number);
    }
}

