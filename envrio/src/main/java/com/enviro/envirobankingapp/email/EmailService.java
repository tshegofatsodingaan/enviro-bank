package com.enviro.envirobankingapp.email;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.dto.ResetPasswordRequest;
import com.enviro.envirobankingapp.services.ThymeleafService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    ThymeleafService thymeleafService;
    @Override
    @Async
    public void sendPasswordToUser(String to, CustomerDto customerDto, String password) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    "utf-8");

            Map<String, Object> variables = new HashMap<>();
            variables.put("name", customerDto.getName());
            variables.put("password", password);

            helper.setTo(to);
            helper.setSubject("Welcome new user");
            helper.setFrom("envirobank.365@gmail.com");
            helper.setText(thymeleafService.createContent("create-new-customer.html", variables), true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }

    }

    @Override
    public void sendResetPasswordLink(String to, ResetPasswordRequest request, String link){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    "utf-8");

            Map<String, Object> variables = new HashMap<>();
            variables.put("link", link);

            helper.setTo(to);
            helper.setSubject("Reset Enviro Bank password");
            helper.setFrom("envirobank.365@gmail.com");
            helper.setText(thymeleafService.createContent("reset-password.html", variables), true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("failed to send email");
            throw new IllegalStateException("failed to send email");
        }
    }

}
