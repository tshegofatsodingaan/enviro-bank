package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> getUserByEmail(String email);

    List<UserEntity> getCustomers();
}
