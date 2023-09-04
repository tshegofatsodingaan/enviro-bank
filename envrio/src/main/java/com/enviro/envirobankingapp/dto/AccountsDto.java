package com.enviro.envirobankingapp.dto;

import com.enviro.envirobankingapp.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountsDto {
    private Long id;
    private Long accountNum;
    private String customerNum;
    private AccountType accountType;
    private BigDecimal accountBalance;
}
