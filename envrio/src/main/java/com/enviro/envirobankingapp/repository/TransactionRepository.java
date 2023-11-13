package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Transaction;
import com.enviro.envirobankingapp.services.PendingTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findTransactionByAccountAccountNum(Integer accountNum);

    List<Transaction> findTransactionByPendingIsTrue();


}
