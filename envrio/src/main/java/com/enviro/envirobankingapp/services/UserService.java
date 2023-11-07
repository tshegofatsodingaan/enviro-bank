package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.exceptions.InvalidCredentialsException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> getUserByEmail(String email);

    void resetPassword(ResetPasswordRequest request) throws InvalidCredentialsException;

    void changePassword(UserEntity user, String newPassword, String confirmPassword) throws InvalidCredentialsException;

    List<UserEntity> getCustomers();
}
