package com.envirobankingapp.envrio.services.impl;

import com.envirobankingapp.envrio.dto.AccountsDto;
import com.envirobankingapp.envrio.dto.TransactionsDto;
import com.envirobankingapp.envrio.entities.AccountEntity;
import com.envirobankingapp.envrio.entities.TransactionEntity;
import com.envirobankingapp.envrio.enums.Accounts;
import com.envirobankingapp.envrio.enums.Transactions;
import com.envirobankingapp.envrio.repository.AccountsRepository;
import com.envirobankingapp.envrio.repository.TransactionsRepository;
import com.envirobankingapp.envrio.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service

public class AccountServiceImpl implements AccountService {
    private AccountEntity account1;

    private  final TransactionsRepository transactionsRepository;
    private final AccountsRepository accountsRepository;
    BigDecimal balance;
    public AccountServiceImpl(TransactionsRepository transactionsRepository, AccountsRepository accountsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
    }




    @Override
    public void withdraw(Long accountNum, BigDecimal amountToWithdraw) {

        Optional<AccountEntity> account = accountsRepository.findById(accountNum);
//        AccountEntity acc = accountsRepository.findByAccountNum(accountNum);
        if(account.isPresent()){
            account1 = account.get();
            System.out.println(account1);

        }else{
            System.out.println("account not found");
        }
        balance = account1.getAccountBalance();
        BigDecimal subtractedAmount = balance.subtract(amountToWithdraw);

        if(balance.compareTo(amountToWithdraw) > 0
                && subtractedAmount.compareTo(BigDecimal.valueOf(1000.00)) >= 0) {
            balance = subtractedAmount;
            account1.setAccountBalance(subtractedAmount);
            accountsRepository.save(account1);

        }

        //dto to entity
        TransactionsDto transactionsDto = new TransactionsDto();
        TransactionEntity transactionEntity = new TransactionEntity();
        AccountEntity newAccountInfo = new AccountEntity();

        transactionEntity.setCustomerNum(account1.getCustomerNum());
        transactionEntity.setTransactionAmount(amountToWithdraw);
        transactionEntity.setAccountNum(account1.getAccountNum());
        transactionEntity.setAccountType(Accounts.SAVINGS);
        transactionEntity.setTypeOfTransaction(Transactions.WITHDRAW);
        TransactionEntity newTransaction = transactionsRepository.save(transactionEntity);


        // entity to dto
        newTransaction.setTransactionId(transactionEntity.getTransactionId());
        newTransaction.setCustomerNum(transactionEntity.getCustomerNum());
        newTransaction.setAccountNum(transactionEntity.getAccountNum());
        newTransaction.setTransactionAmount(transactionEntity.getTransactionAmount());
        newTransaction.setAccountType(transactionEntity.getAccountType());
        newTransaction.setTypeOfTransaction(transactionEntity.getTypeOfTransaction());

        newAccountInfo.setAccountBalance(balance);


    }

    @Override
    public void softDelete(Long accountNum) {
        TransactionEntity transactionEntity = transactionsRepository.findByAccountNum(accountNum);
        transactionEntity.setDeleted(true);
        transactionsRepository.save(transactionEntity);
    }


}