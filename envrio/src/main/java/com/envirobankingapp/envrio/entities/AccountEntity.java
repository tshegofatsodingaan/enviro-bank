package com.envirobankingapp.envrio.entities;

import com.envirobankingapp.envrio.enums.Accounts;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "accounts"
)
public class AccountEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountNum;

    private String customerNum;

    @Enumerated(value = EnumType.STRING)
    private Accounts accountType;

    private BigDecimal accountBalance;

/*    @OneToMany(mappedBy = "accountEntity")
    private List<TransactionEntity> transactions = new ArrayList<>();*/

}
