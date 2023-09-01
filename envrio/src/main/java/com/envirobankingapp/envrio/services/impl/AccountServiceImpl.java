package com.envirobankingapp.envrio.services.impl;

import com.envirobankingapp.envrio.dto.AccountConstants;
import com.envirobankingapp.envrio.entities.AccountEntity;
import com.envirobankingapp.envrio.entities.TransactionEntity;
import com.envirobankingapp.envrio.enums.Accounts;
import com.envirobankingapp.envrio.enums.Transactions;
import com.envirobankingapp.envrio.exceptions.EntityNotFoundException;
import com.envirobankingapp.envrio.exceptions.InsufficientFundsException;
import com.envirobankingapp.envrio.repository.AccountsRepository;
import com.envirobankingapp.envrio.repository.TransactionsRepository;
import com.envirobankingapp.envrio.services.AccountService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountEntity accountEntity;

    private  final TransactionsRepository transactionsRepository;
    private final AccountsRepository accountsRepository;

    private final AccountConstants accountConstants;
    BigDecimal balance;

    public AccountServiceImpl(TransactionsRepository transactionsRepository, AccountsRepository accountsRepository, AccountConstants accountConstants) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
        this.accountConstants = accountConstants;
    }


    @Override
    public void withdraw(Long accountNum, BigDecimal amountToWithdraw) {

        Optional<AccountEntity> account = Optional.ofNullable(accountsRepository.findByAccountNum(accountNum));
        if(account.isPresent()){
            accountEntity = account.get();
        }else{
            System.out.println("account not found");
        }
        if (accountEntity.getAccountType() == Accounts.SAVINGS){
            withdrawFromSavings(amountToWithdraw);
        } if(accountEntity.getAccountType() == Accounts.CURRENT){
            withdrawFromCurrent(amountToWithdraw);
        }

        //dto to entity
        TransactionEntity transactionEntity = new TransactionEntity();
        AccountEntity newAccountInfo = new AccountEntity();
        transactionEntity.setTransactionAmount(amountToWithdraw);
        transactionEntity.setTypeOfTransaction(Transactions.WITHDRAW);
        transactionEntity.setAccountNum(accountEntity);
        transactionEntity.setAccountEntity(accountEntity);
        TransactionEntity newTransaction = transactionsRepository.save(transactionEntity);


        // entity to dto
        newTransaction.setId(transactionEntity.getId());
        newTransaction.setTransactionAmount(transactionEntity.getTransactionAmount());
        newTransaction.setTypeOfTransaction(transactionEntity.getTypeOfTransaction());
        newAccountInfo.setAccountBalance(balance);
    }

    private void withdrawFromSavings(BigDecimal amount){
        balance = accountEntity.getAccountBalance();
        BigDecimal subtractedAmount = balance.subtract(amount);
        if(balance.compareTo(amount) > 0
        && subtractedAmount.compareTo(accountConstants.minimum) >= 0) {
            balance = subtractedAmount;
            accountEntity.setAccountBalance(subtractedAmount);
            accountsRepository.save(accountEntity);
        } else{
            throw new InsufficientFundsException("Savings Account cannot contain amount less than a R1000.00.");
        }
    }

    private void withdrawFromCurrent(BigDecimal amount){
        balance = accountEntity.getAccountBalance();
        BigDecimal availableFunds = balance.add(accountConstants.overdraft);
        BigDecimal subtractedAmount = balance.subtract(amount);
        if(amount.compareTo(availableFunds) < 0){
            balance = subtractedAmount;
            accountEntity.setAccountBalance(subtractedAmount);
            accountsRepository.save(accountEntity);
        } else{
            throw new InsufficientFundsException("You have exceeded your limit.");
        }
    }

    @Override
    public List<TransactionEntity> getTransactionsByAccountNumber(AccountEntity accountNum) {
        System.out.println(accountConstants);
        return transactionsRepository.findByAccountNumAndActive(accountNum, true);
    }


    public void softDelete(UUID id) {
        Optional<TransactionEntity> optionalTransaction = transactionsRepository.findById(id);
        if(optionalTransaction.isPresent()){
            TransactionEntity transaction = optionalTransaction.orElseThrow();
            transaction.setActive(false);
            transactionsRepository.save(transaction);

        } else{
            throw new EntityNotFoundException("Transaction does not exist.");
        }
        //should through entity not found error


    }


}