package com.barsness.budget.service.repository;

import com.barsness.budget.service.domain.TransactionRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRuleRepository extends JpaRepository<TransactionRule, Long> {
}
