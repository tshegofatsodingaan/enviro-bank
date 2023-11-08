package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.exceptions.InvalidCredentialsException;

public interface AuthService {

    AuthResponse signIn(SignInRequest signInRequest) throws InvalidCredentialsException;

    void resetPassword(ResetPasswordRequest request) throws InvalidCredentialsException;

    void changePassword(UserEntity user, String newPassword, String confirmPassword) throws InvalidCredentialsException;
}
