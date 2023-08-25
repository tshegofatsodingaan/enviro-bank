package com.envirobankingapp.envrio.controllers;


import com.envirobankingapp.envrio.services.impl.SavingsAccountImpl;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/api/v1/")
public class AccountController {

    private final SavingsAccountImpl savingsAccount;


    public AccountController(SavingsAccountImpl savingsAccount){
        this.savingsAccount = savingsAccount;
    }


    @GetMapping("/users")
    public void getWithdrawFromSavings(@RequestParam String accountNum, @RequestParam BigDecimal amountToWithdraw){
        savingsAccount.withdraw(accountNum, amountToWithdraw);


    }


}
