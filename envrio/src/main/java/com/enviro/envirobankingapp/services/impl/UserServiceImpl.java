package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
import com.enviro.envirobankingapp.email.EmailSender;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.repository.UserRepository;
import com.enviro.envirobankingapp.services.UserService;
import com.enviro.envirobankingapp.utils.JwtSecurityUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
    public void resetPassword(ResetPasswordRequest request){
        boolean userExists = userRepository.findByEmail(request.getEmail()).isPresent();

        if(!userExists){
            throw new IllegalStateException(request.getEmail() + "is not found.");
        }

        String resetToken = jwtSecurityUtil.generateToken(request.getEmail());
        String link = "http://localhost:9005/api/v1/auth/change-password?token=" + resetToken;
        emailSender.sendResetPasswordLink(request.getEmail(), request, link);
    }

    @Override
    public void changePassword(UserEntity user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
