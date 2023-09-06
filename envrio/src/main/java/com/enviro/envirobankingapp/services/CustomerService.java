package com.enviro.envirobankingapp.services;

import com.enviro.envirobankingapp.dto.CustomerDto;

public interface CustomerService {

    CustomerDto createNewCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(CustomerDto customerDto, long id);
}
