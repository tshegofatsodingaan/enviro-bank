package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.entities.Admin;
import com.enviro.envirobankingapp.entities.User;
import com.enviro.envirobankingapp.repository.AdminRepository;
import com.enviro.envirobankingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private Admin admin;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        admin = adminRepository.findAdminByName(username)
                .orElseThrow(() -> new RuntimeException("Could not find user with username " + username));

        return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(),
                Collections.EMPTY_LIST);
    }
}
