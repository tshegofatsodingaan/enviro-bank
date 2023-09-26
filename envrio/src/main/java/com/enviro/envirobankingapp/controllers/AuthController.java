package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController( AuthService authService) {
        this.authService = authService;

    }


    @PostMapping("/sign-in")
    public AuthResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("reset-password")
    public void resetPassword(){

    }
}


