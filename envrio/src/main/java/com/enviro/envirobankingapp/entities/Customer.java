package com.enviro.envirobankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "customer"
)
public class Customer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private UUID customerId;
    private String name;
    private String surname;
    private Long idNumber;
    private Long phoneNumber;

}
