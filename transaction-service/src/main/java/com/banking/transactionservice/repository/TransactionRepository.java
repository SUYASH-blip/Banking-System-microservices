package com.banking.transactionservice.repository;

import com.banking.transactionservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findBySenderAccountNumberOrderByCreatedAtDesc(String accountNumber);

}
