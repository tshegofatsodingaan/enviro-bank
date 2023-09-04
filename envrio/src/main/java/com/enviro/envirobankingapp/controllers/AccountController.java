package com.enviro.envirobankingapp.controllers;


import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.dto.TransactionsDto;
import com.enviro.envirobankingapp.entities.Account;
import com.enviro.envirobankingapp.exceptions.InsufficientFundsException;
import com.enviro.envirobankingapp.services.AccountService;
import com.enviro.envirobankingapp.services.impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok("withdraw was successful.");
        } catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/transactions/{accountNum}")
    public ResponseEntity<?> getTransactionsByAccountNum(@PathVariable Account accountNum){
        return ResponseEntity.ok(accountService.getTransactionsByAccountNumber(accountNum));
    }


    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> softDeletion(@PathVariable UUID accountId){
        try{
            accountService.softDelete(accountId);
            return ResponseEntity.ok("Transactions have been deleted.");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}