package com.envirobankingapp.envrio.entities;

import com.envirobankingapp.envrio.enums.Accounts;
import com.envirobankingapp.envrio.enums.Transactions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private UUID transactionId;
    private String customerNum;
    private Long accountNum;

    @Enumerated(EnumType.STRING)
    private Transactions typeOfTransaction;

    @Enumerated(EnumType.STRING)
    private Accounts accountType;
    private BigDecimal withdrawalAmount;

}
