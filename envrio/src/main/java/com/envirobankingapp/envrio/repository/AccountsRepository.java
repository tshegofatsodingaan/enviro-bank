package com.envirobankingapp.envrio.repository;

import com.envirobankingapp.envrio.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByAccountNum(Long accountNum);


}
