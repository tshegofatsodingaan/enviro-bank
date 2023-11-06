package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.entities.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void withdraw(Integer accountNumber, BigDecimal amountToWithdraw);

    void transfer(Integer senderAccountNumber, Integer receiverAccountNumber, BigDecimal amountToTransfer);

    List<Transaction> getTransactionsByAccountNumber(int accountNum);
}
