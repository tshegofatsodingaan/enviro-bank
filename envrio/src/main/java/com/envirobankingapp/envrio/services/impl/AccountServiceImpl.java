package com.envirobankingapp.envrio.services.impl;

import com.envirobankingapp.envrio.dto.AccountsDto;
import com.envirobankingapp.envrio.dto.TransactionsDto;
import com.envirobankingapp.envrio.entities.AccountEntity;
import com.envirobankingapp.envrio.entities.TransactionEntity;
import com.envirobankingapp.envrio.enums.Accounts;
import com.envirobankingapp.envrio.enums.Transactions;
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

        // dto to entity
        TransactionsDto transactionsDto = new TransactionsDto();
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setWithdrawalAmount(amountToWithdraw);
        transactionEntity.setAccountType(Accounts.SAVINGS);
        transactionEntity.setTypeOfTransaction(Transactions.WITHDRAW);


        // entity to dto
        transactionsDto.setAccountNum(transactionEntity.getAccountNum());
        transactionsDto.setWithdrawalAmount(transactionEntity.getWithdrawalAmount());
        transactionsDto.setAccountType(Accounts.SAVINGS);
        transactionsDto.setTypeOfTransaction(Transactions.WITHDRAW);



    }




}
