package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Account;
import com.enviro.envirobankingapp.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

//    List<Transaction> findByAccountNumAndActive(Account accountNum, Boolean deleted);

    List<Transaction> findByAccountNum(Account accountNum);

}
