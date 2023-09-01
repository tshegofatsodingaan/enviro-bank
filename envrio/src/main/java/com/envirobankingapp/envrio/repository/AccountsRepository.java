package com.envirobankingapp.envrio.repository;

import com.envirobankingapp.envrio.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {
    Account findByAccountNum(Long accountNum);


}
