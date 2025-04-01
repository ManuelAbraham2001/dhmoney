package com.dh.accounts_service.Exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String cvuOrAlias){
        super("No se encontro una cuenta con el cvu o el alias: " + cvuOrAlias);
    }
}
