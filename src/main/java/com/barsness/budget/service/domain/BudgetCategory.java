package com.barsness.budget.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long budgetId;
    private String categoryName;
    private String categoryDescription;
    private Long categoryParentId;

    public BudgetCategory() {
    }

    public BudgetCategory(Long budgetId, String categoryName, String categoryDescription) {
        this.budgetId = budgetId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public BudgetCategory(Long budgetId, String categoryName, String categoryDescription, Long categoryParentId) {
        this.budgetId = budgetId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryParentId = categoryParentId;
    }

    public Long getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(Long categoryParentId) {
        this.categoryParentId = categoryParentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
