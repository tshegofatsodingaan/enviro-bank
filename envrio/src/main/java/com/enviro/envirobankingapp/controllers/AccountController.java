package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.services.AccountService;
import com.enviro.envirobankingapp.services.CustomerService;
import com.enviro.envirobankingapp.services.impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> softDeletion(@PathVariable Long accountId){
        try{
            accountService.softDelete(accountId);
            return ResponseEntity.ok("Account deactivated.");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public List<?> getAllAccounts(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize){
        if(id != null){
            Optional<Customer> customerId = customerService.getCustomerById(id);
            //            return accountTransferObj != null ? Collections.singletonList(accountTransferObj) : Collections.emptyList();
            return accountService.getAccountById(customerId);
        }
        return accountService.getAccounts(pageNo, pageSize);

    }
}
