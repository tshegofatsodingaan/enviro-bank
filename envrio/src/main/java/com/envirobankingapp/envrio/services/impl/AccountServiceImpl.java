package com.envirobankingapp.envrio.services.impl;

import com.envirobankingapp.envrio.dto.AccountConstants;
import com.envirobankingapp.envrio.entities.Account;
import com.envirobankingapp.envrio.entities.Transaction;
import com.envirobankingapp.envrio.enums.Accounts;
import com.envirobankingapp.envrio.enums.Transactions;
import com.envirobankingapp.envrio.exceptions.EntityNotFoundException;
import com.envirobankingapp.envrio.exceptions.InsufficientFundsException;
import com.envirobankingapp.envrio.repository.AccountsRepository;
import com.envirobankingapp.envrio.repository.TransactionsRepository;
import com.envirobankingapp.envrio.services.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private Account account;
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

        Optional<Account> account = Optional.ofNullable(accountsRepository.findByAccountNum(accountNum));
        if(account.isPresent()){
            this.account = account.get();
        }else{
            System.out.println("account not found");
        }
        if (this.account.getAccountType() == Accounts.SAVINGS){
            withdrawFromSavings(amountToWithdraw);
        } if(this.account.getAccountType() == Accounts.CURRENT){
            withdrawFromCurrent(amountToWithdraw);
        }

        //dto to entity
        Transaction transaction = new Transaction();
        Account newAccountInfo = new Account();
        transaction.setTransactionAmount(amountToWithdraw);
        transaction.setTypeOfTransaction(Transactions.WITHDRAW);
        transaction.setAccountNum(this.account);
        transaction.setAccount(this.account);
        Transaction newTransaction = transactionsRepository.save(transaction);


        // entity to dto
        newTransaction.setId(transaction.getId());
        newTransaction.setTransactionAmount(transaction.getTransactionAmount());
        newTransaction.setTypeOfTransaction(transaction.getTypeOfTransaction());
        newAccountInfo.setAccountBalance(balance);
    }


    private void withdrawFromSavings(BigDecimal amount){
        balance = account.getAccountBalance();
        BigDecimal subtractedAmount = balance.subtract(amount);
        if(balance.compareTo(amount) > 0
        && subtractedAmount.compareTo(accountConstants.minimum) >= 0) {
            balance = subtractedAmount;
            account.setAccountBalance(subtractedAmount);
            accountsRepository.save(account);
        } else{
            throw new InsufficientFundsException("Savings Account cannot contain amount less than a R1000.00.");
        }
    }


    private void withdrawFromCurrent(BigDecimal amount){
        balance = account.getAccountBalance();
        BigDecimal availableFunds = balance.add(accountConstants.overdraft);
        BigDecimal subtractedAmount = balance.subtract(amount);
        if(amount.compareTo(availableFunds) < 0){
            balance = subtractedAmount;
            account.setAccountBalance(subtractedAmount);
            accountsRepository.save(account);
        } else{
            throw new InsufficientFundsException("You have exceeded your limit.");
        }
    }


    @Override
    public List<Transaction> getTransactionsByAccountNumber(Account accountNum) {
        return transactionsRepository.findByAccountNumAndActive(accountNum, true);
    }


    public void softDelete(UUID id) {
        Optional<Transaction> optionalTransaction = transactionsRepository.findById(id);
        if(optionalTransaction.isPresent()){
            Transaction transaction = optionalTransaction.orElseThrow();
            transaction.setActive(false);
            transactionsRepository.save(transaction);

        } else{
            throw new EntityNotFoundException("Transaction does not exist.");
        }
    }
}