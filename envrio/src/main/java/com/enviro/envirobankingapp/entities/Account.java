package com.enviro.envirobankingapp.entities;

import com.enviro.envirobankingapp.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
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

    private Integer accountNum;

    private String customerNum;

    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    private BigDecimal accountBalance;

    private BigDecimal availableBalance;

    private Boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    // field name should be "customer"
    private Customer customer;

}
