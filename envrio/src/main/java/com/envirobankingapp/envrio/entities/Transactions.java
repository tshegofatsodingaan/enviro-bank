package com.envirobankingapp.envrio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Transactions {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private UUID transactionId;
    private String customerNum;
    private Long accountNum;
    private String typeOfTransaction;
    private String accountType;
    private int withdrawalAmount;

}
