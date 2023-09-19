package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Role;
import com.enviro.envirobankingapp.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(UserRole name);
}
