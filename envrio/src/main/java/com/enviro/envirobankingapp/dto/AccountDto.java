package com.enviro.envirobankingapp.dto;

import com.enviro.envirobankingapp.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private Long id;
    private Integer accountNum;
    private String customerNum;
    private AccountType accountType;
    private BigDecimal accountBalance;
    private BigDecimal availableBalance;
    private Boolean active;
}
