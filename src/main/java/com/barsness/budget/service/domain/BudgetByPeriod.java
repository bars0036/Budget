package com.barsness.budget.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class BudgetByPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long BudgetCategoryId;
    private BigDecimal value;
    private LocalDate budgetStartDate;
    private LocalDate budgetEndDate;
    private String type;

    public BudgetByPeriod() {
    }

    public BudgetByPeriod(Long budgetCategoryId, BigDecimal value, LocalDate budgetStartDate, LocalDate budgetEndDate, String type) {
        BudgetCategoryId = budgetCategoryId;
        this.value = value;
        this.budgetStartDate = budgetStartDate;
        this.budgetEndDate = budgetEndDate;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBudgetCategoryId() {
        return BudgetCategoryId;
    }

    public void setBudgetCategoryId(Long budgetCategoryId) {
        BudgetCategoryId = budgetCategoryId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getBudgetStartDate() {
        return budgetStartDate;
    }

    public void setBudgetStartDate(LocalDate budgetStartDate) {
        this.budgetStartDate = budgetStartDate;
    }

    public LocalDate getBudgetEndDate() {
        return budgetEndDate;
    }

    public void setBudgetEndDate(LocalDate budgetEndDate) {
        this.budgetEndDate = budgetEndDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
