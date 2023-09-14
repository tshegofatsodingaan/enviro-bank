package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.dto.SignUpResponse;

public interface AuthService {

    SignUpResponse signIn(SignInRequest signInRequest);

}
