package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.AuthResponse;
import com.enviro.envirobankingapp.dto.ChangePasswordRequest;
import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
import com.enviro.envirobankingapp.dto.SignInRequest;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.services.AuthService;
import com.enviro.envirobankingapp.services.impl.UserServiceImpl;
import com.enviro.envirobankingapp.utils.JwtSecurityUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private UserServiceImpl userService;
    private JwtSecurityUtil jwtSecurityUtil;


    public AuthController(AuthService authService, UserServiceImpl userService, JwtSecurityUtil jwtSecurityUtil) {
        this.authService = authService;
        this.userService = userService;
        this.jwtSecurityUtil = jwtSecurityUtil;
    }


    @PostMapping(value = "/sign-in", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AuthResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        userService.resetPassword(request);
        return ResponseEntity.ok("Password-Reset mail sent successfully");
    }


    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("token") String token, @RequestBody ChangePasswordRequest passwordRequest){
        // TODO: extract the name/email from the token
        String email = jwtSecurityUtil.extractEmail(token);

        Optional<UserEntity> optionalUser = userService.findByEmail(email);

        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            userService.changePassword(user, passwordRequest.getNewPassword());
            return ResponseEntity.ok("Password has been updated.");
        }
        return ResponseEntity.notFound().build();
    }
}


