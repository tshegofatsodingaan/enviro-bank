package com.enviro.envirobankingapp.services.impl;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.repository.CustomerRepository;
import com.enviro.envirobankingapp.services.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    public CustomerDto createNewCustomer(CustomerDto customerDto) {
        Customer customer = mapDTOtoEntity(customerDto);
        Customer newCustomer =customerRepository.save(customer);

        return mapEntityToDTO(newCustomer);

    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, long id){
        Customer customer = customerRepository.findById(id).orElseThrow();
        mapEntityToDTO(customer);

        Customer updatedCustomer = customerRepository.save(customer);
        return mapEntityToDTO(updatedCustomer);
    }

    private Customer mapDTOtoEntity(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setIdNumber(customerDto.getIdNumber());
        customer.setName(customerDto.getName());
        customer.setSurname(customerDto.getSurname());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customer;
    }

    private CustomerDto mapEntityToDTO(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setIdNumber(customer.getIdNumber());
        customerDto.setName(customer.getName());
        customerDto.setSurname(customer.getSurname());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }
}
