package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> newCustomer(@RequestBody CustomerDto customerDto){
        return new ResponseEntity<>(customerService.createNewCustomer(customerDto), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable(name = "id") long id){
        CustomerDto customerResponse = customerService.updateCustomer(customerDto, id);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }
}
