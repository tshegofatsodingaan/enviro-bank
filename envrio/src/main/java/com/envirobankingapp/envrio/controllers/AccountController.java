package com.envirobankingapp.envrio.controllers;


import com.envirobankingapp.envrio.dto.AccountsDto;
import com.envirobankingapp.envrio.dto.TransactionsDto;
import com.envirobankingapp.envrio.entities.AccountEntity;
import com.envirobankingapp.envrio.exceptions.EntityNotFoundException;
import com.envirobankingapp.envrio.exceptions.InsufficientFundsException;
import com.envirobankingapp.envrio.exceptions.WithdrawalException;
import com.envirobankingapp.envrio.services.AccountService;
import com.envirobankingapp.envrio.services.impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountServiceImpl accountService){
        this.accountService = accountService;
    }


    @PostMapping ("/withdraw")
    public ResponseEntity<String> withdrawTransaction(@RequestBody TransactionsDto transactionsDto){

        try{
            accountService.withdraw(transactionsDto.getAccountNum(), transactionsDto.getTransactionAmount());
            return ResponseEntity.ok("withdraw was successful");
        } catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/transactions/{accountNum}")
    public ResponseEntity<?> getTransactionsByAccountNum(@PathVariable AccountEntity accountNum){
        return ResponseEntity.ok(accountService.getTransactionsByAccountNumber(accountNum));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> softDeletion(@PathVariable UUID accountId){
        try{
            accountService.softDelete(accountId);
            return ResponseEntity.ok("Transactions have been deleted");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
