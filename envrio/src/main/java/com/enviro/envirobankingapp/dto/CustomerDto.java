package com.enviro.envirobankingapp.dto;

import com.enviro.envirobankingapp.enums.UserRole;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDto {


    private String name;
    private String surname;
    @Pattern(regexp = "\\d{13}",message = "Invalid phone number.")
    private String idNumber;
    @Pattern(regexp = "\\d{10}",message = "Invalid phone number.")
    private String phoneNumber;
    private String email;
}
