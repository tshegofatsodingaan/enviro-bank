package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.AccountsDto;
import com.enviro.envirobankingapp.entities.Customer;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void withdraw(Integer accountNum, BigDecimal amountToWithdraw) {

        if (amountToWithdraw.compareTo(BigDecimal.ZERO) <= 0)
            throw new InsufficientFundsException("Cannot withdraw amount less than 0.");

        Optional<Account> account = Optional.ofNullable(accountRepository.findByAccountNumAndActive(accountNum, true));

        if(!account.isPresent())
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
        transaction.setAccountNum(this.account);
        transaction.setAccount(this.account);
        Transaction newTransaction = transactionRepository.save(transaction);


        // entity to dto
        newTransaction.setId(transaction.getId());
        newTransaction.setTransactionAmount(transaction.getTransactionAmount());
        newTransaction.setTypeOfTransaction(transaction.getTypeOfTransaction());
        newAccountInfo.setAccountBalance(balance);
    }

    private AccountsDto mapEntityToDto(Account account){
        AccountsDto accountsResponse = new AccountsDto();
        accountsResponse.setId(account.getId());
        accountsResponse.setAccountBalance(account.getAccountBalance());
        accountsResponse.setAccountNum(account.getAccountNum());
        accountsResponse.setCustomerNum(account.getCustomerNum());
        accountsResponse.setAccountType(account.getAccountType());
        accountsResponse.setActive(account.getActive());
        return accountsResponse;
    }


    private void withdrawFromSavings(BigDecimal amount){
        balance = account.getAccountBalance();
        BigDecimal subtractedAmount = balance.subtract(amount);

        if(!(balance.compareTo(amount) > 0
        && subtractedAmount.compareTo(accountConstants.minimum) >= 0)) {
            throw new InsufficientFundsException("Savings Account cannot contain amount less than a R" + accountConstants.minimum + ".");
        }
        updateAccountEntity(subtractedAmount);
    }


    private void withdrawFromCurrent(BigDecimal amount){
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
    public List<Transaction> getTransactionsByAccountNumber(Account accountNum) {
        return transactionRepository.findByAccountNum(accountNum);
    }


    public void softDelete(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if(!(optionalAccount.isPresent())){
            throw new EntityNotFoundException("Account does not exist.");
        }

        Account transaction = optionalAccount.orElseThrow();
        transaction.setActive(false);
        accountRepository.save(transaction);
    }

    @Override
    public List<AccountsDto> getAccounts(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Account> accounts = accountRepository.findAll(pageable);

        return accounts.getContent().stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Account> getAccountById(Optional<Customer> id){
        return accountRepository.findByCustomerIdAndActive(id, true);

    }
}