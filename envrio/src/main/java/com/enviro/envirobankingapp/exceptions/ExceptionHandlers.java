package com.enviro.envirobankingapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


@RestControllerAdvice // defines a global exception handling for Spring MVC controllers
public class ExceptionHandlers {
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Object> message(InsufficientFundsException e){

        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(errorDetails.getErrorStatus()).body(errorDetails);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> message(EntityNotFoundException e){

        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),HttpStatus.NOT_FOUND);
        return  ResponseEntity.status(errorDetails.getErrorStatus()).body(errorDetails);
    }

    @ExceptionHandler(PsqlException.class)
    public ResponseEntity<Object> message(PsqlException e){

        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        return  ResponseEntity.status(errorDetails.getErrorStatus()).body(errorDetails);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> message(InvalidCredentialsException e){

        ErrorDetails errorDetails = new ErrorDetails(e.getMessage(), HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(errorDetails.getErrorStatus()).body(errorDetails);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String ,String> handleInvalidArgument(MethodArgumentNotValidException exception){
//
//    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
//                                                              WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
