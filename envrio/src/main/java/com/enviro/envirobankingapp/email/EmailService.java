package com.enviro.envirobankingapp.email;

import com.enviro.envirobankingapp.dto.CustomerDto;
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

import java.nio.charset.StandardCharsets;
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
    public void sendToNewUser(String to, CustomerDto customerDto, String password) {
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
            helper.setFrom("dingaan@gmail.com");
            helper.setText(thymeleafService.createContent("create-new-customer.html", variables), true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }

    }

    @Override
    public void sendToResetPassword(String to){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    "utf-8");

            helper.setTo(to);
            helper.setSubject("Reset your Enviro Bank password");
            helper.setFrom("dingaan@gmail.com");
            helper.setText(thymeleafService.createContent("reset-password.html", null), true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("failed to send email");
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendMailCreateCustomer(CustomerDto dto) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setTo(dto.getEmail());

            Map<String, Object> variables = new HashMap<>();
            variables.put("full_name", dto.getName());
            variables.put("surname", dto.getSurname());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
