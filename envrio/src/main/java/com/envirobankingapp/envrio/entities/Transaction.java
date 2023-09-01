package com.envirobankingapp.envrio.entities;

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
@Table(
        name = "transactions"
)
public class Transaction {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Transactions typeOfTransaction;

    private BigDecimal transactionAmount;

    private Boolean active = true;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountNum", referencedColumnName = "accountNum")
    private Account accountNum;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}
