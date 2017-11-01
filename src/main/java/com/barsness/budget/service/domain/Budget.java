package com.barsness.budget.service.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="budgetId", referencedColumnName = "id")
    private List<BudgetCategory> budgetCategories = new ArrayList<>();

    public Budget() {
    }

    public Budget(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Budget(String name, String description, List<BudgetCategory> budgetCategories) {
        this.name = name;
        this.description = description;
        this.budgetCategories = budgetCategories;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
