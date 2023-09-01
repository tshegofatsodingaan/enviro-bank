package com.envirobankingapp.envrio.repository;

import com.envirobankingapp.envrio.entities.AccountEntity;
import com.envirobankingapp.envrio.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByAccountNumAndActive(AccountEntity accountNum, Boolean deleted);

//    TransactionEntity findByAccountId(AccountEntity accountId);

//    TransactionEntity findById(Long accountId);
}
