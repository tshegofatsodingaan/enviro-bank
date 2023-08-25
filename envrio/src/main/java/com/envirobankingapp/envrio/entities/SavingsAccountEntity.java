package com.envirobankingapp.envrio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class SavingsAccountEntity {

    @Id
    @GeneratedValue()
    private UUID transactionId;
    private String customerNum;
    private UUID accountNum;
    private String typeOfTransaction;
    private String accountType;
    private int withdrawalAmount;

}

