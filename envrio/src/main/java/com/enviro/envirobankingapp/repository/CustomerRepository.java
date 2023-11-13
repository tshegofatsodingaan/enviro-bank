package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.services.CustomerSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

@Query(value = "SELECT u.id, u.name, u.email, u.surname, COUNT(a.id) as numberOfAccounts " +
    "FROM public.users as u " +
    "left join public.account as a on u.id = a.customer_id " +
    "group by u.name, u.id ", nativeQuery = true)

List<CustomerSummary> getCustomerAccountSummary();
}
