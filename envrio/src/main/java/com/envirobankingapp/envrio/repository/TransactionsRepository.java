package com.envirobankingapp.envrio.repository;

import com.envirobankingapp.envrio.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<TransactionEntity, UUID> {

    TransactionEntity findByAccountNum(Long accountNum);
    List<TransactionEntity> findByDeletedFalse();

}
