package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.registration.EmailValidator;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;

    public String register(CustomerDto request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return "works";
    }
}
