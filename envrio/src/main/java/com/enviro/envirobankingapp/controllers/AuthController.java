package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.dto.SignInResponse;
import com.enviro.envirobankingapp.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController( AuthService authService) {
        this.authService = authService;

    }


    @GetMapping
    public ResponseEntity<String> sayHello(){
        System.out.println("hiiiiiiiiiiiiiiii");
        return ResponseEntity.ok("Hello from secured endpoint.");
    }


    @PostMapping("/sign-in")
    public AuthResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }
}
