package com.enviro.envirobankingapp.dto;

import com.enviro.envirobankingapp.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class TransactionDto {

    private UUID id;
    private Integer accountNum;
    private Integer receiverAccountNum;
    private TransactionType typeOfTransaction;
    private BigDecimal transactionAmount;
    private Date dateOfTransaction;


}
