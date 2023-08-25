package com.envirobankingapp.envrio.services.impl;

import com.envirobankingapp.envrio.services.AccountService;

import java.math.BigDecimal;

public class CurrentAccountImpl implements AccountService {

    public BigDecimal overdraft;
    public BigDecimal balance;

    public CurrentAccountImpl(BigDecimal overdraft, BigDecimal balance) {
        this.overdraft = overdraft;
        this.balance = balance;
    }


    @Override
    public void withdraw(String accountNum, BigDecimal amountToWithdraw) {

        BigDecimal subtractedAmount = balance.subtract(amountToWithdraw);

        BigDecimal maximumAmount = balance.add(overdraft);
        if(maximumAmount.compareTo(amountToWithdraw) > 0){
            balance = subtractedAmount;
        }



    }
}
