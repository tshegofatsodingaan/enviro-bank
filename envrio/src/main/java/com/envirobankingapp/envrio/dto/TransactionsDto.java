package com.envirobankingapp.envrio.dto;

import java.util.UUID;

public class TransactionsDto {

    private UUID transactionId;
    private String customerNum;
    private Long accountNum;
    private String typeOfTransaction;
    private String accountType;
    private int withdrawalAmount;
}
