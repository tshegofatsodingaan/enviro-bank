package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.entities.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);
}
