package com.envirobankingapp.envrio.dto;

import com.envirobankingapp.envrio.enums.Accounts;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountsDto {
    private Long accountNum;
    private Accounts accountType;
    private String customerNum;
    private BigDecimal accountBalance;
    private BigDecimal overdraft;
}
