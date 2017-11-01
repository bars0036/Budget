package com.barsness.budget.service.repository;

import com.barsness.budget.service.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        List<Transaction> findByTransactionDateAndDescriptionAndValue(LocalDateTime transactionDate, String description, BigDecimal value);
        List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
        Transaction findById(Long id);
        List<Transaction> findAll();

}
