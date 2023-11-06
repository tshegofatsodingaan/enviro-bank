package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.exceptions.PsqlException;
import com.enviro.envirobankingapp.repository.CustomerSummary;
import com.enviro.envirobankingapp.services.CustomerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public Customer getCustomersById(@RequestParam(value = "id", required = false) String id) {
        Long Id = Long.parseLong(id);
        return customerService.getCustomerById(Id);

    }

    @PreAuthorize(value = "hasRole({'ADMIN'})")
    @GetMapping("/everyone")
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerService.getCustomers();
        return customers.stream().map(customer -> modelMapper.map(customer, Customer.class)).toList();
    }


    @PreAuthorize(value = "hasRole({'ADMIN'})")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomerDto> newCustomer(@RequestBody @Valid CustomerDto customerDto) throws PsqlException {
        return new ResponseEntity<>(customerService.createNewCustomer(customerDto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole({'ADMIN'})")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid CustomerDto customerDto, @PathVariable(name = "id") long id) throws EntityNotFoundException {

        CustomerDto customerResponse = customerService.updateCustomer(customerDto, id);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);

    }

    @PreAuthorize(value = "hasRole({'ADMIN'})")
    @GetMapping("/customer-summary")
    public ResponseEntity<List<CustomerSummary>> getCustomerSummary() {
        List<CustomerSummary> customerList = customerService.getNumberOfAccounts();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

}
