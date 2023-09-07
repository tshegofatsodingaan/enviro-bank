package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.repository.CustomerRepository;
import com.enviro.envirobankingapp.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;


    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper){
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public CustomerDto createNewCustomer(CustomerDto customerDto) {
        Customer customer = mapDTOtoEntity(customerDto);
        Customer newCustomer = customerRepository.save(customer);

        return mapEntityToDTO(newCustomer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, long id){
        Customer customer = customerRepository.findById(id).orElseThrow();
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
