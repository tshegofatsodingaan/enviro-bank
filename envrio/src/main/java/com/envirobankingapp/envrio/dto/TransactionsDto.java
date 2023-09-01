package com.envirobankingapp.envrio.dto;

import com.envirobankingapp.envrio.enums.Accounts;
import com.envirobankingapp.envrio.enums.Transactions;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionsDto {

    private UUID id;
    private Long accountNum;
    private Transactions typeOfTransaction;
    private BigDecimal transactionAmount;


}
