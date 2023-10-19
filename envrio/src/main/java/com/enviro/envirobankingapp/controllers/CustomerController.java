package com.enviro.envirobankingapp.controllers;

import com.enviro.envirobankingapp.dto.CustomerDto;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.exceptions.EntityNotFoundException;
import com.enviro.envirobankingapp.repository.CustomerSummary;
import com.enviro.envirobankingapp.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }


//    @PreAuthorize(value = "hasRole({'USER'})")
    @GetMapping()
    public Optional<Customer> getCustomersById(@RequestParam(value = "id", required = false) String id){
        Long Id = Long.parseLong(id);
        return customerService.getCustomerById(Id);

    }

    @PreAuthorize(value = "hasRole({'ADMIN'})")
    @GetMapping("/everyone")
    public List<Customer> getAllCustomers(){
        return customerService.getCustomers();
    }


    @PreAuthorize(value = "hasRole({'ADMIN'})")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomerDto> newCustomer(@RequestBody @Valid CustomerDto customerDto){
        return new ResponseEntity<>(customerService.createNewCustomer(customerDto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole({'ADMIN'})")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid CustomerDto customerDto, @PathVariable(name = "id") long id){
        try{
            CustomerDto customerResponse = customerService.updateCustomer(customerDto, id);
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PreAuthorize(value = "hasRole({'ADMIN'})")
    @GetMapping("/customer-summary")
    public ResponseEntity<List<CustomerSummary>> getCustomerSummary(){
        List<CustomerSummary> customerList = customerService.getNumberOfAccounts();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

}
