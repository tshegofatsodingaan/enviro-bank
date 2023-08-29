package com.envirobankingapp.envrio.services;

import com.envirobankingapp.envrio.entities.AccountEntity;

import java.math.BigDecimal;

public interface AccountService {
    void withdraw(Long accountNum, BigDecimal
            amountToWithdraw);

    void softDelete(Long id);
}
