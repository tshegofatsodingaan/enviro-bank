package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.SignInRequest;

public interface AuthService {

    AuthResponse signIn(SignInRequest signInRequest);

}
