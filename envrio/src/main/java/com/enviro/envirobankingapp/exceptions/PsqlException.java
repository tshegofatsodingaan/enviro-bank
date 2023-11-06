package com.enviro.envirobankingapp.exceptions;

public class PsqlException  extends RuntimeException{
    public PsqlException(String message){
        super(message);
    }
}
