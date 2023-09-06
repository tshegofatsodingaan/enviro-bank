package com.enviro.envirobankingapp.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private String name;
    private String surname;
    private Long idNumber;
    private Long phoneNumber;
}
