package com.envirobankingapp.envrio.controllers;


import com.envirobankingapp.envrio.dto.AccountsDto;
import com.envirobankingapp.envrio.dto.TransactionsDto;
import com.envirobankingapp.envrio.exceptions.WithdrawalException;
import com.envirobankingapp.envrio.services.impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;


    public AccountController(AccountServiceImpl accountService){
        this.accountService = accountService;
    }


    @PostMapping ("/withdraw")
    public ResponseEntity<String> withdrawTransaction(@RequestBody TransactionsDto transactionsDto){

        try{
            accountService.withdraw(transactionsDto.getAccountNum(), transactionsDto.getTransactionAmount());
            return ResponseEntity.ok("withdraw was successful");
        } catch (WithdrawalException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{accountNum}")
    public ResponseEntity<String> softDeletion(@PathVariable Long accountNum){
        accountService.softDelete(accountNum);
        return ResponseEntity.ok("transactions have been deleted");
    }

}
