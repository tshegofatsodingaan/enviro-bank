package com.envirobankingapp.envrio.repository;

import com.envirobankingapp.envrio.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<AccountEntity, Long> {
}
