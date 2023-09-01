package com.envirobankingapp.envrio.services;

import com.envirobankingapp.envrio.dto.TransactionsDto;
import com.envirobankingapp.envrio.entities.AccountEntity;
import com.envirobankingapp.envrio.entities.TransactionEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    void withdraw(Long accountNum, BigDecimal amountToWithdraw);

    List<TransactionEntity> getTransactionsByAccountNumber(AccountEntity accountNum);

    void softDelete(UUID accountNum);
}
