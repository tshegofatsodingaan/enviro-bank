package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.exceptions.InvalidCredentialsException;

public interface AuthService {

    AuthResponse signIn(SignInRequest signInRequest) throws InvalidCredentialsException;

}
