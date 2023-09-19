package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.repository.UserRepository;
import com.enviro.envirobankingapp.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByName(email);
    }
}
