package com.enviro.envirobankingapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(
        name = "customer"
)
public class Customer extends UserEntity {

}
