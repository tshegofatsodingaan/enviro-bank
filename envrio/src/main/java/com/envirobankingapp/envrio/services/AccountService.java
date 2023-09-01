package com.envirobankingapp.envrio.services;

import com.envirobankingapp.envrio.entities.Account;
import com.envirobankingapp.envrio.entities.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    void withdraw(Long accountNum, BigDecimal amountToWithdraw);
    List<Transaction> getTransactionsByAccountNumber(Account accountNum);
    void softDelete(UUID accountNum);
}
