package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.exceptions.PsqlException;
import com.enviro.envirobankingapp.repository.CustomerSummary;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDto createNewCustomer(CustomerDto customerDto) throws PsqlException;

    CustomerDto updateCustomer(CustomerDto customerDto, long id) throws EntityNotFoundException;

    Customer getCustomerById(Long id);

    List<Customer> getCustomers();

    List<CustomerSummary> getNumberOfAccounts();

    // int getNumberOfAccounts(String customerId);
}
