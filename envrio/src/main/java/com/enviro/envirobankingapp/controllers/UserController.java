package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.services.impl.UserServiceImpl;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/all-users")
    public List<UserEntity> getAllUsers(){
        return userService.getCustomers();
    }
}
