package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.email.EmailSender;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.repository.UserRepository;
import com.enviro.envirobankingapp.services.UserService;
import com.enviro.envirobankingapp.utils.JwtSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtSecurityUtil jwtSecurityUtil;
    private final EmailSender emailSender;

    public UserServiceImpl(UserRepository userRepository, JwtSecurityUtil jwtSecurityUtil, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.jwtSecurityUtil = jwtSecurityUtil;
        this.emailSender = emailSender;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    @Override
    public List<UserEntity> getCustomers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }
}
