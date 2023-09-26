package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.entities.Admin;
import com.enviro.envirobankingapp.entities.Role;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.enums.UserRole;
import com.enviro.envirobankingapp.repository.AdminRepository;
import com.enviro.envirobankingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private Admin admin;



    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByNameOrEmail(usernameOrEmail, usernameOrEmail) //change to user
                .orElseThrow(() -> new RuntimeException("Could not find user with username or email: " + usernameOrEmail));

        Set<GrantedAuthority> authorities = userEntity
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName())).collect(Collectors.toSet());


        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(),
                authorities);
    }

}