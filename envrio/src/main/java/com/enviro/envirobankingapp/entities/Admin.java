package com.enviro.envirobankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(
        name = "admin"
)
public class Admin extends User{

}
