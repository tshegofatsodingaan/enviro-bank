package com.enviro.envirobankingapp.entities;

import com.enviro.envirobankingapp.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "users"
)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String idNumber;

    private String phoneNumber;

//    @Enumerated(value = EnumType.STRING)
//    private Set<UserRole> roles = new HashSet<>();
}
