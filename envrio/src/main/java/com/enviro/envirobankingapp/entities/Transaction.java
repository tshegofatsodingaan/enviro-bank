package com.enviro.envirobankingapp.entities;

import com.enviro.envirobankingapp.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "transaction"
)
public class Transaction {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransactionType typeOfTransaction;

    private BigDecimal transactionAmount;

    private Integer receiverAccountNum;

    private Integer senderAccountNum;

    private Date dateOfTransaction;

    private Boolean pending = true;

    private BigDecimal amountAdded;

    private BigDecimal amountDeducted;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

}
