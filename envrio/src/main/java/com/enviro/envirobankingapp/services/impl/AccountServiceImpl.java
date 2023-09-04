package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.entities.Transaction;
import com.enviro.envirobankingapp.enums.AccountType;
import com.enviro.envirobankingapp.enums.TransactionType;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.exceptions.InsufficientFundsException;
import com.enviro.envirobankingapp.repository.AccountRepository;
import com.enviro.envirobankingapp.repository.TransactionRepository;
import com.enviro.envirobankingapp.services.AccountService;
import com.enviro.envirobankingapp.dto.AccountConstants;
import com.enviro.envirobankingapp.entities.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private Account account;
    private  final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountConstants accountConstants;
    BigDecimal balance;


    public AccountServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountConstants accountConstants) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountConstants = accountConstants;
    }


    @Override
    public void withdraw(Long accountNum, BigDecimal amountToWithdraw) {

        if (amountToWithdraw.compareTo(BigDecimal.ZERO) <= 0){
            throw new InsufficientFundsException("Cannot withdraw amount less than 0.");
        }else{
            Optional<Account> account = Optional.ofNullable(accountRepository.findByAccountNum(accountNum));
            if(account.isPresent()){
                this.account = account.get();
            }else{
                throw new EntityNotFoundException("Enter existing account.");
            }
            if (this.account.getAccountType() == AccountType.SAVINGS){
                withdrawFromSavings(amountToWithdraw);
            } if(this.account.getAccountType() == AccountType.CURRENT){
                withdrawFromCurrent(amountToWithdraw);
            }
        }

        //dto to entity
        Transaction transaction = new Transaction();
        Account newAccountInfo = new Account();
        transaction.setTransactionAmount(amountToWithdraw);
        transaction.setTypeOfTransaction(TransactionType.WITHDRAW);
        transaction.setAccountNum(this.account);
        transaction.setAccount(this.account);
        Transaction newTransaction = transactionRepository.save(transaction);


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
            updateAccountEntity(subtractedAmount);
        } else{
            throw new InsufficientFundsException("Savings Account cannot contain amount less than a R" + accountConstants.minimum + ".");
        }
    }


    private void withdrawFromCurrent(BigDecimal amount){
        balance = account.getAccountBalance();
        BigDecimal availableFunds = balance.add(accountConstants.overdraft);
        BigDecimal subtractedAmount = balance.subtract(amount);
        if(amount.compareTo(availableFunds) < 0){
            updateAccountEntity(subtractedAmount);
        } else{
            throw new InsufficientFundsException("You have exceeded your limit.");
        }
    }

    public void updateAccountEntity(BigDecimal subtractedAmount){
        balance = subtractedAmount;
        account.setAccountBalance(subtractedAmount);
        accountRepository.save(account);
    }


    @Override
    public List<Transaction> getTransactionsByAccountNumber(Account accountNum) {
        return transactionRepository.findByAccountNumAndActive(accountNum, true);
    }


    public void softDelete(UUID id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if(optionalTransaction.isPresent()){
            Transaction transaction = optionalTransaction.orElseThrow();
            transaction.setActive(false);
            transactionRepository.save(transaction);

        } else{
            throw new EntityNotFoundException("Transaction does not exist.");
        }
    }
}