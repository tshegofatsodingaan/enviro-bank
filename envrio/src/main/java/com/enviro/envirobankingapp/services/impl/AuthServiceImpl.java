package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
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
    private final EmailSender emailSender;

    @Override
    public AuthResponse signIn(SignInRequest signInRequest) throws InvalidCredentialsException {

        UserEntity user = userRepository.findByEmail(signInRequest.getEmail())

                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            return createAuthResponse(user);
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }


    @Override
    public void resetPassword(ResetPasswordRequest request) throws InvalidCredentialsException {
        boolean userExists = userRepository.findByEmail(request.getEmail()).isPresent();

        if (!userExists) {
            return;
        }

        String resetToken = jwtSecurityUtil.generateToken(request.getEmail());
        String link = "http://localhost:4200/change-password?token=" + resetToken;
        emailSender.sendResetPasswordLink(request.getEmail(), request, link);
    }


    @Override
    public void changePassword(UserEntity user, String newPassword, String confirmPassword) throws InvalidCredentialsException {
        if (newPassword.equals(confirmPassword)) {
            if (!passwordEncoder.matches(confirmPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
            } else {
                throw new InvalidCredentialsException("new password should not be the same as the current one");
            }
        } else {
            throw new InvalidCredentialsException("passwords do not match.");
        }


        userRepository.save(user);
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

