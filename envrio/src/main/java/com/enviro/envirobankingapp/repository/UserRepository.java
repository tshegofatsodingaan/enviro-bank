package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
