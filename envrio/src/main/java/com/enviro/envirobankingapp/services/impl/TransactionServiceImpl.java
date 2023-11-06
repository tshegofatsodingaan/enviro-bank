package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.AccountConstants;
import com.enviro.envirobankingapp.entities.Account;
import com.enviro.envirobankingapp.entities.Transaction;
import com.enviro.envirobankingapp.enums.AccountType;
import com.enviro.envirobankingapp.enums.TransactionType;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.exceptions.InsufficientFundsException;
import com.enviro.envirobankingapp.repository.AccountRepository;
import com.enviro.envirobankingapp.repository.TransactionRepository;
import com.enviro.envirobankingapp.services.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountConstants accountConstants;
    private Account account;
    BigDecimal balance;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountConstants accountConstants){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountConstants = accountConstants;
    }


    /***
     * Performs a withdrawal based on type of account
     * @param accountNumber Specific to an account
     * @param amountToWithdraw Subtracted from balance
     */
    @Override
    public void withdraw(Integer accountNumber, BigDecimal amountToWithdraw) {

        if (amountToWithdraw.compareTo(BigDecimal.ZERO) <= 0 && amountToWithdraw.compareTo(account.getAccountBalance()) <= 0)
            throw new InsufficientFundsException("Cannot withdraw amount less than 0.");

        Optional<Account> account = Optional.ofNullable(accountRepository.findByAccountNumAndActive(accountNumber, true));

        if(account.isEmpty())
            throw new EntityNotFoundException("This account does not exist.");

        this.account = account.get();

        if (this.account.getAccountType() == AccountType.SAVINGS)
            withdrawFromSavings(amountToWithdraw);

        if(this.account.getAccountType() == AccountType.CURRENT)
            withdrawFromCurrent(amountToWithdraw);


        //dto to entity
        Transaction transaction = new Transaction();
        Account newAccountInfo = new Account();
        transaction.setTransactionAmount(amountToWithdraw);
        transaction.setTypeOfTransaction(TransactionType.WITHDRAW);
        transaction.setAccount(this.account);
        transaction.setAccount(this.account);

        Transaction newTransaction = transactionRepository.save(transaction);


        // entity to dto
        newTransaction.setId(transaction.getId());
        newTransaction.setTransactionAmount(transaction.getTransactionAmount());
        newTransaction.setTypeOfTransaction(transaction.getTypeOfTransaction());
        newAccountInfo.setAccountBalance(balance);
    }


    /***
     * Performs withdrawal from savings account
     * @param amount Amount to withdraw
     */
    private void withdrawFromSavings(BigDecimal amount) throws InsufficientFundsException{
        balance = account.getAccountBalance();
        BigDecimal subtractedAmount = balance.subtract(amount);

        if(!(balance.compareTo(amount) > 0
                && subtractedAmount.compareTo(accountConstants.minimum) >= 0)) {
            throw new InsufficientFundsException("Savings Account cannot contain amount less than a R" + accountConstants.minimum + ".");
        }
        updateAccountEntity(subtractedAmount);
    }


    /***
     * Performs withdrawal from current account
     * @param amount Amount to withdraw
     */
    private void withdrawFromCurrent(BigDecimal amount) throws InsufficientFundsException{
        balance = account.getAccountBalance();
        BigDecimal availableFunds = balance.add(accountConstants.overdraft);
        BigDecimal subtractedAmount = balance.subtract(amount);

        if(!(amount.compareTo(availableFunds) < 0)){
            throw new InsufficientFundsException("You have exceeded your limit.");
        }
        updateAccountEntity(subtractedAmount);
    }

    public void updateAccountEntity(BigDecimal subtractedAmount){
        balance = subtractedAmount;
        account.setAccountBalance(subtractedAmount);
        accountRepository.save(account);
    }


    @Override
    public void transfer(Integer senderAccountNumber, Integer receiverAccountNumber, BigDecimal amountToTransfer){


        if (amountToTransfer.compareTo(BigDecimal.ZERO) <= 0)
            throw new InsufficientFundsException("Amount to transfer must be greater than 0.");

        Optional<Account> senderAccount = Optional.ofNullable(accountRepository.findByAccountNumAndActive(senderAccountNumber, true));
        Optional<Account> receiverAccount = Optional.ofNullable(accountRepository.findByAccountNumAndActive(receiverAccountNumber, true));


        if(senderAccount.isEmpty() || receiverAccount.isEmpty())
            throw new EntityNotFoundException("This account does not exist.");

        this.account = senderAccount.get();


        if (this.account.getAccountType() == AccountType.SAVINGS  || this.account.getAccountType() == AccountType.CURRENT)
            transferBetweenSavingsAndCurrent(amountToTransfer);

        this.account = receiverAccount.get();
        BigDecimal updatedAmount = receiverAccount.get().getAccountBalance().add(amountToTransfer);
        this.account.setAccountBalance(updatedAmount);

        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(amountToTransfer);
        transaction.setTypeOfTransaction(TransactionType.TRANSFER);
        transaction.setAccount(this.account);
        transaction.setReceiverAccountNum(receiverAccountNumber);
        transaction.setDateOfTransaction(new Date());

        transactionRepository.save(transaction);
    }


    private void transferBetweenSavingsAndCurrent(BigDecimal amountToTransfer) throws InsufficientFundsException{
        BigDecimal senderBalance = this.account.getAccountBalance();
        if(senderBalance.compareTo(amountToTransfer) < 0){
            throw new InsufficientFundsException("Insufficient funds for transfer.");
        }
        BigDecimal subtractedAmount = senderBalance.subtract(amountToTransfer);
        account.setAccountBalance(subtractedAmount);
    }


    @Override
    public List<Transaction> getTransactionsByAccountNumber(int accountNum) {
        return transactionRepository.findTransactionByAccountAccountNum(accountNum);
    }
}
