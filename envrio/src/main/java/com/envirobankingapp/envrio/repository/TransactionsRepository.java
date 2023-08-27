package com.envirobankingapp.envrio.repository;

import com.envirobankingapp.envrio.enums.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
}
