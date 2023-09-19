package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.services.AccountService;
import com.enviro.envirobankingapp.services.CustomerService;
import com.enviro.envirobankingapp.services.impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    public AccountController(AccountServiceImpl accountService, CustomerService customerService){
        this.accountService = accountService;
        this.customerService = customerService;
    }


    @PreAuthorize(value = "hasRole({'USER'})")
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> softDeletion(@PathVariable Long accountId){
        try{
            accountService.softDelete(accountId);
            return ResponseEntity.ok("Account deactivated.");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize(value = "hasRole({'USER'})")
    @GetMapping("/any")
    public List<?> getAllAccounts(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize){
        if(id != null){
            Optional<Customer> customerId = customerService.getCustomerById(id);
            return accountService.getAccountById(customerId);
        }
        return accountService.getAccounts(pageNumber, pageSize);
    }
}
