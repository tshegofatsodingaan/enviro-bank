package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.entities.Admin;
import com.enviro.envirobankingapp.entities.Role;
import com.enviro.envirobankingapp.entities.UserEntity;
import com.enviro.envirobankingapp.enums.UserRole;
import com.enviro.envirobankingapp.repository.AdminRepository;
import com.enviro.envirobankingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
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

        Set<String> roles = userEntity.getRoles().stream().map(Role::getName).map(UserRole::name).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(),
                getAuthorities(roles));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }
}
