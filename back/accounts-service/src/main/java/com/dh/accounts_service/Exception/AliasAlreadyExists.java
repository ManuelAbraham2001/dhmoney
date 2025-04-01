package com.dh.accounts_service.Exception;

public class AliasAlreadyExists extends RuntimeException{
    public AliasAlreadyExists(String message){
        super(message);
    }
}
