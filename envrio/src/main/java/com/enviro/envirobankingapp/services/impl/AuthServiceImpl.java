package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.email.EmailSender;
import com.enviro.envirobankingapp.entities.Role;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.exceptions.InvalidCredentialsException;
import com.enviro.envirobankingapp.repository.UserRepository;
import com.enviro.envirobankingapp.services.AuthService;
import com.enviro.envirobankingapp.utils.JwtSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtSecurityUtil jwtSecurityUtil;
    private final UserRepository userRepository;

    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {

        UserEntity user = userRepository.findByEmail(signInRequest.getEmail())

                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            return createAuthResponse(user);
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }


    private AuthResponse createAuthResponse(UserEntity userEntity) {
        Set<String> userRoles = userEntity.getRoles().stream().map(Role::getName)
                .collect(Collectors.toSet());
        String userId = userEntity.getId().toString();

        return new AuthResponse(jwtSecurityUtil.generateToken(userEntity.getName(),
                userRoles,
                userEntity.getEmail(),
                userEntity.getId(),
                userEntity.getSurname()),
                userRoles,
                userEntity.getName(),
                userEntity.getSurname(),
                userId);
    }


}

