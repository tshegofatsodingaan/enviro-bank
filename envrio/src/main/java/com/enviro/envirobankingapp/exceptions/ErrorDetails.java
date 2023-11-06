package com.enviro.envirobankingapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
//    private  Date timestamp;
    private  String message;
//    private  String details;
    private  HttpStatus errorStatus;
}
