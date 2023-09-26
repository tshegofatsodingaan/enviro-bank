package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
