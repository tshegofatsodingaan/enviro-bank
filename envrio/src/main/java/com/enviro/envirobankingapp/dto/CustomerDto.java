package com.enviro.envirobankingapp.dto;

import com.enviro.envirobankingapp.enums.UserRole;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDto {


    private String name;
    private String surname;
    @Pattern(regexp = "\\d{13}",message = "Invalid identification number.")
    @Pattern(regexp = "(([0-9]{2})(0|1)([0-9])([0-3])([0-9]))([ ]?)(([0-9]{4})([ ]?)([0-1][8]([ ]?)[0-9]))",message = "Invalid identification number.")
    private String idNumber;
    @Pattern(regexp = "^0[0-9]{9}$",message = "Please enter valid phone number, without the country code.")
    private String phoneNumber;
    private String email;
}

// 0207290750083
