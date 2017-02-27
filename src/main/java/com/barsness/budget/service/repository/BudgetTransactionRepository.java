package com.barsness.budget.service.repository;


import com.barsness.budget.service.domain.BudgetTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetTransactionRepository extends JpaRepository<BudgetTransaction, Long>{
}
