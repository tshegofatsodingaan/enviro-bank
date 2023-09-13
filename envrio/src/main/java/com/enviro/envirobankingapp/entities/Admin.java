package com.enviro.envirobankingapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(
        name = "admin"
)
public class Admin extends User{

}
