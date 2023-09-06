package com.enviro.envirobankingapp.dto;

import com.enviro.envirobankingapp.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionsDto {

    private UUID id;
    private Integer accountNum;
    private TransactionType typeOfTransaction;
    private BigDecimal transactionAmount;


}
