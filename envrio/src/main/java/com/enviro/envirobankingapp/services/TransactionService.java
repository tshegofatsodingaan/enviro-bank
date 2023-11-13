package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.entities.Transaction;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void withdraw(Integer accountNumber, BigDecimal amountToWithdraw) throws EntityNotFoundException;

    void transfer(Integer senderAccountNumber, Integer receiverAccountNumber, BigDecimal amountToTransfer) throws EntityNotFoundException, InsufficientFundsException;

    List<Transaction> getTransactionsByAccountNumber(int accountNum);

void  updateTransaction(Transaction transaction);
}
