package com.enviro.envirobankingapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(
        name = "customer"
)
public class Customer extends User {

}
