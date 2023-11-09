package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.TransactionDto;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.exceptions.InsufficientFundsException;
import com.enviro.envirobankingapp.services.AccountService;
import com.enviro.envirobankingapp.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/transactions")

public class TransactionController {

//    private final AccountService accountService;

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize(value = "hasRole({'USER'})")
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawTransaction(@RequestBody TransactionDto transactionDto) throws EntityNotFoundException {

        try {
            transactionService.withdraw(transactionDto.getAccountNum(), transactionDto.getTransactionAmount());
            return ResponseEntity.ok("withdraw was successful.");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/transfer", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> transferTransaction(@RequestBody TransactionDto transactionDto) throws InsufficientFundsException, EntityNotFoundException {
        transactionService.transfer(transactionDto.getAccountNum(), transactionDto.getReceiverAccountNum(), transactionDto.getTransactionAmount());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{accountNum}")
    public ResponseEntity<?> getTransactionsByAccountNum(@PathVariable String accountNum) {
        int accountNumber = Integer.parseInt(accountNum);
        return ResponseEntity.ok(transactionService.getTransactionsByAccountNumber(accountNumber));
    }
}
