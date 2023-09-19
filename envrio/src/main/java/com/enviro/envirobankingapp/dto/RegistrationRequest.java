package com.enviro.envirobankingapp.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String name;
    private final String surname;
    private final String email;
    private final String password;
}
