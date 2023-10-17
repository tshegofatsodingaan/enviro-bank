package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDto createNewCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(CustomerDto customerDto, long id);

    Optional<Customer> getCustomerById(Long id);

    List<Customer> getCustomers();

   // int getNumberOfAccounts(String customerId);
}
