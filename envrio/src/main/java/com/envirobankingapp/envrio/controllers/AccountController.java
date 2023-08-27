package com.envirobankingapp.envrio.controllers;


import com.envirobankingapp.envrio.services.impl.AccountServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {

    private final AccountServiceImpl savingsAccount;


    public AccountController(AccountServiceImpl savingsAccount){
        this.savingsAccount = savingsAccount;
    }


    @GetMapping("/users")
    public void getWithdrawFromSavings(@RequestParam String accountNum, @RequestParam BigDecimal amountToWithdraw){
        savingsAccount.withdraw(accountNum, amountToWithdraw);


    }


}
