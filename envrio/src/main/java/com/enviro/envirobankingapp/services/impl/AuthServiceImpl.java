package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.dto.SignUpResponse;
import com.enviro.envirobankingapp.entities.User;
import com.enviro.envirobankingapp.services.AuthService;
import com.enviro.envirobankingapp.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserServiceImpl userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @Override
    public SignUpResponse signIn(SignInRequest signInRequest) {
        User user = userService.findByEmail(signInRequest.getName())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            return new SignUpResponse("this is the token");
        }

        throw new RuntimeException("Invalid credentials");
    }

}

