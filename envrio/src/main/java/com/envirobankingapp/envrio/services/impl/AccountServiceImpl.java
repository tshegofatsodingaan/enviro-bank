package com.envirobankingapp.envrio.services.impl;

import com.envirobankingapp.envrio.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    public BigDecimal balance;


    @Override
    public void withdraw(String accountNum, BigDecimal amountToWithdraw) {

        BigDecimal subtractedAmount = balance.subtract(amountToWithdraw);

        if(balance.compareTo(amountToWithdraw) > 0
                && Integer.parseInt(String.valueOf(subtractedAmount)) >= 1000) {
            balance = subtractedAmount;
        }
    }
}
