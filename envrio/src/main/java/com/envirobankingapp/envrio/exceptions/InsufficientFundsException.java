package com.envirobankingapp.envrio.exceptions;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String message){
        super(message);
    }
}
