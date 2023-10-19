package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.TransactionDto;
import com.enviro.envirobankingapp.entities.Account;
import com.enviro.envirobankingapp.exceptions.InsufficientFundsException;
import com.enviro.envirobankingapp.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/transactions")

public class TransactionController {

    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize(value = "hasRole({'USER'})")
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawTransaction(@RequestBody TransactionDto transactionDto){

        try{
            accountService.withdraw(transactionDto.getAccountNum(), transactionDto.getTransactionAmount());
            return ResponseEntity.ok("withdraw was successful.");
        } catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize(value = "hasRole({'USER'})")
    @PostMapping(value = "/transfer", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> transferTransaction(@RequestBody TransactionDto transactionDto){
        try{
            accountService.transfer(transactionDto.getAccountNum(), transactionDto.getReceiverAccountNum(), transactionDto.getTransactionAmount());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @PreAuthorize(value = "hasRole({'USER'})")
    @GetMapping("/{accountNum}")
    public ResponseEntity<?> getTransactionsByAccountNum(@PathVariable Account accountNum){
        return ResponseEntity.ok(accountService.getTransactionsByAccountNumber(accountNum));
    }
}
