package com.enviro.envirobankingapp.entities;

import com.enviro.envirobankingapp.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "account"
)
public class Account {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountNum;

    private String customerNum;

    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    private BigDecimal accountBalance;

/*    @OneToMany(mappedBy = "accountEntity")
    private List<TransactionEntity> transactions = new ArrayList<>();*/

}
