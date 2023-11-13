package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.ChangePasswordRequest;
import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.exceptions.InvalidCredentialsException;
import com.enviro.envirobankingapp.services.AuthService;
import com.enviro.envirobankingapp.utils.JwtSecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtSecurityUtil jwtSecurityUtil;


    public AuthController(AuthService authService, JwtSecurityUtil jwtSecurityUtil) {
        this.authService = authService;
        this.jwtSecurityUtil = jwtSecurityUtil;
    }


    @PostMapping(value = "/sign-in", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AuthResponse signIn(@RequestBody SignInRequest signInRequest) throws InvalidCredentialsException {
        return authService.signIn(signInRequest);
    }

    @PostMapping(value = "/reset-password", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) throws InvalidCredentialsException{
        authService.resetPassword(request);
        return ResponseEntity.ok("Password-Reset mail sent successfully");
    }


    @PostMapping(value = "/change-password", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> changePassword(@RequestParam("token") String token,
                                                 @RequestBody @Valid ChangePasswordRequest passwordRequest) throws InvalidCredentialsException{

        String email = jwtSecurityUtil.extractEmail(token);
        Optional<UserEntity> optionalUser = authService.findByEmail(email);

        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            authService.changePassword(user, passwordRequest.getNewPassword(), passwordRequest.getConfirmPassword());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


