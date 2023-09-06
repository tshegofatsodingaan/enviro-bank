package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.AccountsDto;
import com.enviro.envirobankingapp.entities.Transaction;
import com.enviro.envirobankingapp.entities.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    void withdraw(Integer accountNum, BigDecimal amountToWithdraw);
    List<Transaction> getTransactionsByAccountNumber(Account accountNum);
    void softDelete(UUID accountNum);

    List<AccountsDto> getAccounts(int pageNo, int pageSize);

    AccountsDto getAccountById(long id);

//    List<AccountsDto> findAccountById(Long id);
}
