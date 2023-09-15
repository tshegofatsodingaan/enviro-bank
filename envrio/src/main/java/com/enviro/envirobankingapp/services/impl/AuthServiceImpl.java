package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.dto.SignInResponse;
import com.enviro.envirobankingapp.entities.Role;
import com.enviro.envirobankingapp.entities.User;
import com.enviro.envirobankingapp.enums.UserRole;
import com.enviro.envirobankingapp.services.AuthService;
import com.enviro.envirobankingapp.services.UserService;
import com.enviro.envirobankingapp.utils.JwtSecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtSecurityUtil jwtSecurityUtil;

    private final UserService userService;
    public AuthServiceImpl(PasswordEncoder passwordEncoder, JwtSecurityUtil jwtSecurityUtil, UserServiceImpl userService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtSecurityUtil = jwtSecurityUtil;
        this.userService = userService;
    }


    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        User user = userService.findByEmail(signInRequest.getName())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            return createAuthResponse(user);
        }

        throw new RuntimeException("Invalid credentials");
    }

    private AuthResponse createAuthResponse(User user) {
        Set<UserRole> userRoles = user.getRoles().stream().map(Role::getName)
                .collect(Collectors.toSet());
        return new AuthResponse(jwtSecurityUtil.generateToken(user.getName(), userRoles));
    }

}

