package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.email.EmailSender;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.repository.CustomerRepository;
import com.enviro.envirobankingapp.repository.UserRepository;
import com.enviro.envirobankingapp.services.CustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final EmailSender emailSender;

    private final String generatedPassword = RandomStringUtils.randomAlphanumeric(10);
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository, ModelMapper modelMapper, EmailSender emailSender){
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.emailSender = emailSender;
    }


    /***
     * Creates a new account user
     * @param customerDto Based on customer requirements
     * @return Data Transfer Object to client
     */
    @Override
    public CustomerDto createNewCustomer(CustomerDto customerDto) {
        boolean userExists = userRepository.findByEmail(customerDto.getEmail()).isPresent();
        if (userExists){
           throw new IllegalStateException("email already exists");
        }
        String generatedPassword = RandomStringUtils.randomAlphanumeric(10);
        String encodedPassword = passwordEncoder.encode(generatedPassword);

        Customer customer = mapDTOtoEntity(customerDto);
        customer.setPassword(encodedPassword);
        Customer newCustomer = userRepository.save(customer);
        emailSender.send(customerDto.getEmail(), buildEmail(customerDto.getName(), generatedPassword));
        return mapEntityToDTO(newCustomer);
    }


    /***
     * Updates existing customer
     * @param customerDto Based on customer requirements
     * @param id Must be specific to existing customer
     * @return Data Transfer Object to client
     */
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, long id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This customer does not exist."));
        customer.setName(customerDto.getName());
        customer.setSurname(customerDto.getSurname());
        customer.setIdNumber(customerDto.getIdNumber());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(customer);
        return mapEntityToDTO(updatedCustomer);
    }


    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    private Customer mapDTOtoEntity(CustomerDto customerDto){
        return modelMapper.map(customerDto, Customer.class);
    }

    private CustomerDto mapEntityToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    private String buildEmail(String name, String password) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Your temporary password is: " +generatedPassword+ ". Please use your email to sign in.  </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\" \">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
