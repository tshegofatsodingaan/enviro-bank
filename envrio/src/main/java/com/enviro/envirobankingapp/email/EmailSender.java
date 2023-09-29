package com.enviro.envirobankingapp.email;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.dto.ResetPasswordRequest;

public interface EmailSender {
    void sendPasswordToUser(String to, CustomerDto customerDto, String password);

    void sendResetPasswordLink(String to, ResetPasswordRequest request, String link);

}
