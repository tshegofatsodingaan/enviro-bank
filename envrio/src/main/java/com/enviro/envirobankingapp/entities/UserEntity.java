package com.enviro.envirobankingapp.entities;

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
public abstract class UserEntity {
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_id")
    private Set<Role> roles = new HashSet<>();
}
