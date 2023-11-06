package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.AccountDto;
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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;


    public AccountServiceImpl(AccountRepository accountRepository,
                              ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }


    private AccountDto mapEntityToDto(Account account){
        return modelMapper.map(account, AccountDto.class);
    }

    //@Scheduled(cron = "* * * * * *")
    public void scheduledTransfer(   ){
//        transferBetweenSavingsAndCurrent(amountToTransfer);
    }


    /***
     * Deactivates an account specific to a customer
     * @param id Specific customer
     */
    public void softDelete(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if(optionalAccount.isEmpty()){
            throw new EntityNotFoundException("Account does not exist.");
        }

        Account transaction = optionalAccount.orElseThrow();
        transaction.setActive(false);
        accountRepository.save(transaction);
    }


    /***
     * Retrieves all accounts from Enviro Bank
     * @param pageNo Page number
     * @param pageSize Number of items in a page
     * @return Accounts in a form of a list
     */
    @Override
    public List<AccountDto> getAccounts(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Account> accounts = accountRepository.findAll(pageable);

        return accounts.getContent().stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }


    /***
     * Retrieves account(s) specific to a customer
     * @param id Specific customer
     * @return Account(s) in a form of a list
     */
    @Override
    public List<Account> getAccountById(Long id){
        return accountRepository.findByCustomerIdAndActive(id, true);

    }

    @Override
    public List<Account> getAccountByAccountNumber(int accountNum){
        return accountRepository.findAccountByAccountNum(accountNum);
    }
}