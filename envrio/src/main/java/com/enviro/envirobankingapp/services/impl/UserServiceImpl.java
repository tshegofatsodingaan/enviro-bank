package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
import com.enviro.envirobankingapp.email.EmailSender;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.exceptions.InvalidCredentialsException;
import com.enviro.envirobankingapp.repository.UserRepository;
import com.enviro.envirobankingapp.services.UserService;
import com.enviro.envirobankingapp.utils.JwtSecurityUtil;
import io.swagger.models.auth.In;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void resetPassword(ResetPasswordRequest request) throws InvalidCredentialsException{
        boolean userExists = userRepository.findByEmail(request.getEmail()).isPresent();

        if(!userExists){
            throw new InvalidCredentialsException(request.getEmail() + " is not found.");
        }

        String resetToken = jwtSecurityUtil.generateToken(request.getEmail());
        String link = "http://localhost:4200/change-password?token=" + resetToken;
        emailSender.sendResetPasswordLink(request.getEmail(), request, link);
    }

    @Override
    public void changePassword(UserEntity user, String newPassword, String confirmPassword) throws InvalidCredentialsException {
        if(newPassword.equals(confirmPassword)){
          if (!passwordEncoder.matches(confirmPassword, user.getPassword())){
                user.setPassword(passwordEncoder.encode(newPassword));
            } else{
                throw new InvalidCredentialsException("new password should not be the same as the current one");
            }
        } else {
            throw new InvalidCredentialsException("passwords do not match.");
        }


        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getCustomers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }
}
