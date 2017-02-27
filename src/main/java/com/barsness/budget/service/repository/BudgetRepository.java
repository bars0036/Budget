package com.barsness.budget.service.repository;

import com.barsness.budget.service.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByName(String name);

}
