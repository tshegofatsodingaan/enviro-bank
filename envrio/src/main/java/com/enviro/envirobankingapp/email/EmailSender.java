package com.enviro.envirobankingapp.email;

import com.enviro.envirobankingapp.dto.CustomerDto;

public interface EmailSender {
    void sendToNewUser(String to, CustomerDto customerDto, String password);

    void sendToResetPassword(String to, CustomerDto customerDto, String password, String link);

    void sendMailCreateCustomer(CustomerDto dto);
}
