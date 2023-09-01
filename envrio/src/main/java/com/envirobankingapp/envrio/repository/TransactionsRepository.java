package com.envirobankingapp.envrio.repository;

import com.envirobankingapp.envrio.entities.Account;
import com.envirobankingapp.envrio.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByAccountNumAndActive(Account accountNum, Boolean deleted);

}
