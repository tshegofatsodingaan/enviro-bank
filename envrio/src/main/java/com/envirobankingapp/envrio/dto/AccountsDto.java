package com.envirobankingapp.envrio.dto;

import com.envirobankingapp.envrio.enums.Accounts;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountsDto {
    private Long id;
    private Long accountNum;
    private String customerNum;
    private Accounts accountType;
    private BigDecimal accountBalance;
}
