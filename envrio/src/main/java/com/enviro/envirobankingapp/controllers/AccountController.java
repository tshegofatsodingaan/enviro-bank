package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.AccountDto;
import com.enviro.envirobankingapp.entities.Account;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.services.AccountService;
import com.enviro.envirobankingapp.services.CustomerService;
import com.enviro.envirobankingapp.services.impl.AccountServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public AccountController(AccountServiceImpl accountService, CustomerService customerService, ModelMapper modelMapper){
        this.accountService = accountService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
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

    @GetMapping()
    public List<AccountDto> getAllAccounts(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "accountNum", required = false) String accountNum,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize){
        if(id != null){
            Long Id = Long.parseLong(id);

            List<Account> accounts =  accountService.getAccountById(Id);
            return accounts.stream().map(account -> modelMapper.map(account, AccountDto.class)).toList();
        }
        if(accountNum != null){
            int accountNumber = Integer.parseInt(accountNum);

            List<Account> accounts = accountService.getAccountByAccountNumber(accountNumber);
            return accounts.stream().map(account -> modelMapper.map(account, AccountDto.class)).toList();
        }
        return accountService.getAccounts(pageNumber, pageSize);
    }

    @GetMapping("specific-account")
    public List<Account> getAccountByAccountNum(@RequestParam int accountNum){
        List<Account> account = accountService.getAccountByAccountNumber(accountNum);
        return account.stream().map(account1 -> modelMapper.map(account1, Account.class)).toList();

    }
}
