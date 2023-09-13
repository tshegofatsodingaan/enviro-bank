package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.repository.CustomerRepository;
import com.enviro.envirobankingapp.repository.UserRepository;
import com.enviro.envirobankingapp.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository, ModelMapper modelMapper){
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    /***
     * Creates a new account user
     * @param customerDto Based on customer requirements
     * @return Data Transfer Object to client
     */
    @Override
    public CustomerDto createNewCustomer(CustomerDto customerDto) {
        Customer customer = mapDTOtoEntity(customerDto);
        Customer newCustomer = userRepository.save(customer);

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
}
