package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.entities.Role;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.enums.UserRole;
import com.enviro.envirobankingapp.repository.UserRepository;
import com.enviro.envirobankingapp.services.AuthService;
import com.enviro.envirobankingapp.services.UserService;
import com.enviro.envirobankingapp.utils.JwtSecurityUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtSecurityUtil jwtSecurityUtil;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final UserRepository userRepository;
    public AuthServiceImpl(PasswordEncoder passwordEncoder, JwtSecurityUtil jwtSecurityUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtSecurityUtil = jwtSecurityUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {

        UserEntity userEntity = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (passwordEncoder.matches(signInRequest.getPassword(), userEntity.getPassword())) {
            return createAuthResponse(userEntity);
        }

        throw new RuntimeException("Invalid credentials");
    }



    private AuthResponse createAuthResponse(UserEntity userEntity) {
        Set<UserRole> userRoles = userEntity.getRoles().stream().map(Role::getName)
                .collect(Collectors.toSet());
        return new AuthResponse(jwtSecurityUtil.generateToken(userEntity.getName(), userRoles, userEntity.getEmail()));
    }

}

