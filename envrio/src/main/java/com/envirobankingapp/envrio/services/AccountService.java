package com.envirobankingapp.envrio.services;

import java.math.BigDecimal;

public interface AccountService {
    public void withdraw(String accountNum, BigDecimal
            amountToWithdraw);
}
