package com.barsness.budget.service.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long budgetId;
    private String categoryName;
    private String categoryDescription;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="category_Parent_Id")
    @JsonBackReference
    private BudgetCategory parentCategory;

    @OneToMany(mappedBy="parentCategory", cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<BudgetCategory> budgetCategories = new ArrayList<>();


    public BudgetCategory() {
    }

    public BudgetCategory(String categoryName, String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public BudgetCategory(String categoryName, String categoryDescription, BudgetCategory parentCategory) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parentCategory = parentCategory;
    }

    public BudgetCategory(String categoryName, String categoryDescription, BudgetCategory parentCategory, List<BudgetCategory> budgetCategories) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parentCategory = parentCategory;
        this.budgetCategories = budgetCategories;
    }

    public BudgetCategory(Long budgetId, String categoryName, String categoryDescription) {
        this.budgetId = budgetId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public BudgetCategory(Long budgetId, String categoryName, String categoryDescription, BudgetCategory parentCategory) {
        this.budgetId = budgetId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parentCategory = parentCategory;
    }

    public BudgetCategory(Long budgetId, String categoryName, String categoryDescription, BudgetCategory parentCategory, List<BudgetCategory> budgetCategories) {
        this.budgetId = budgetId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parentCategory = parentCategory;
        this.budgetCategories = budgetCategories;
    }

    public void addBudgetCategory(BudgetCategory budgetCategory){
        budgetCategory.setParentCategory(this);
        this.budgetCategories.add(budgetCategory);
    }

    public BudgetCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(BudgetCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<BudgetCategory> getBudgetCategories() {
        return budgetCategories;
    }

    public void setBudgetCategories(List<BudgetCategory> budgetCategories) {
        this.budgetCategories = budgetCategories;
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
