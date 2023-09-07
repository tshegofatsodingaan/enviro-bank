package com.enviro.envirobankingapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private final Date timestamp;
    private final String message;
    private final String details;
}
