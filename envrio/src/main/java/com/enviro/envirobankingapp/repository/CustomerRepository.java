package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
