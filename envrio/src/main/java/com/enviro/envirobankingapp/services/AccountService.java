package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.AccountDto;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.entities.Transaction;
import com.enviro.envirobankingapp.entities.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    void withdraw(Integer accountNum, BigDecimal amountToWithdraw);
    List<Transaction> getTransactionsByAccountNumber(Account accountNum);
    void softDelete(Long accountNum);

    List<AccountDto> getAccounts(int pageNo, int pageSize);

    List<Account> getAccountById(Optional<Customer> id);

//    List<AccountsDto> findAccountById(Long id);
}
