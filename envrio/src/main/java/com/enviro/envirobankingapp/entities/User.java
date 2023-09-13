package com.enviro.envirobankingapp.entities;

import com.enviro.envirobankingapp.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "user"
)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String idNumber;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
}
