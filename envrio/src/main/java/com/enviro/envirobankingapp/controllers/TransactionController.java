package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.TransactionsDto;
import com.enviro.envirobankingapp.entities.Account;
import com.enviro.envirobankingapp.exceptions.InsufficientFundsException;
import com.enviro.envirobankingapp.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/transactions")
public class TransactionController {

    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawTransaction(@RequestBody TransactionsDto transactionsDto){

        try{
            accountService.withdraw(transactionsDto.getAccountNum(), transactionsDto.getTransactionAmount());
            return ResponseEntity.ok("withdraw was successful.");
        } catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("{accountNum}")
    public ResponseEntity<?> getTransactionsByAccountNum(@PathVariable Account accountNum){
        return ResponseEntity.ok(accountService.getTransactionsByAccountNumber(accountNum));
    }
}
