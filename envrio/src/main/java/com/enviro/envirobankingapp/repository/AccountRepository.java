package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumAndActive(Integer accountNum, Boolean active);
    List<Account> findByCustomerIdAndActive(Long customerId, Boolean active);
    Account findAccountByAccountNum(Integer accountNumber);

    List<Account> findAccountByAccountNum(int accountNum);
}
