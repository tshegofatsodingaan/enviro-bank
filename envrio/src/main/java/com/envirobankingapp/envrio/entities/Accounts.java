package com.envirobankingapp.envrio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Accounts {
    @Id
    @GeneratedValue (
            strategy = GenerationType.IDENTITY
    )
    private Long accountNum;
    private String accountType;
    private int accountBalance;
    private int overdraft;
}
