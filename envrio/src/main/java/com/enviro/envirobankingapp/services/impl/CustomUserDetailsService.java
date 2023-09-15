package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.entities.Admin;
import com.enviro.envirobankingapp.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    private Admin admin;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        admin = adminRepository.findAdminByName(username)
                .orElseThrow(() -> new RuntimeException("Could not find user with username " + username));

        return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(),
                Collections.EMPTY_LIST);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }
}
