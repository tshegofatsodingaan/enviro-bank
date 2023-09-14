package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
}
