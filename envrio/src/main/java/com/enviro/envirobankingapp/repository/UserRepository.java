package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByNameOrEmail(String name, String email);



}
