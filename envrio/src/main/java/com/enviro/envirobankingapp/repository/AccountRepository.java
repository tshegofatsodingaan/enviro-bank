package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.dto.AccountsDto;
import com.enviro.envirobankingapp.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNum(Integer accountNum);

}
