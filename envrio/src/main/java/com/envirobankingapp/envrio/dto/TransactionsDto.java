package com.envirobankingapp.envrio.dto;

import com.envirobankingapp.envrio.enums.Accounts;
import com.envirobankingapp.envrio.enums.Transactions;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionsDto {

    private UUID transactionId;
    private String customerNum;
    private Long accountNum;
    private Transactions typeOfTransaction;
    private Accounts accountType;
    private BigDecimal withdrawalAmount;
}
