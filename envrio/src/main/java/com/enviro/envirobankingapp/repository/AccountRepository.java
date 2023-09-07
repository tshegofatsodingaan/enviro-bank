package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Account;
import com.enviro.envirobankingapp.entities.Customer;
import com.enviro.envirobankingapp.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNum(Integer accountNum);

//    List<Account> findByAccountNumAndActive(Account accountNum, Boolean deleted);
    List<Account> findByCustomerIdAndActive(Optional<Customer> customerId, Boolean active);
}
