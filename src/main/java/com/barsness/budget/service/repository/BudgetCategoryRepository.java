package com.barsness.budget.service.repository;


import com.barsness.budget.service.domain.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long>{

}
