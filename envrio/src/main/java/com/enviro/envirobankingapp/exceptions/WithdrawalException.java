package com.enviro.envirobankingapp.exceptions;

public class WithdrawalException extends RuntimeException {
    public WithdrawalException(String message) {
        super("Bad Request");
    }
}
