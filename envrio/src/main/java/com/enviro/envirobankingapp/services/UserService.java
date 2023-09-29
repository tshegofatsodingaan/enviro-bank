package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
import com.enviro.envirobankingapp.entities.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> getUserByEmail(String email);

    void resetPassword(ResetPasswordRequest request);

    void changePassword(UserEntity user, String newPassword);
}
