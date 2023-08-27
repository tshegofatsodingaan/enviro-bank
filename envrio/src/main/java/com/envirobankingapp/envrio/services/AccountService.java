package com.envirobankingapp.envrio.services;

import java.math.BigDecimal;

public interface AccountService {
    void withdraw(String accountNum, BigDecimal
            amountToWithdraw);
}
