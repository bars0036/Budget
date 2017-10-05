package com.barsness.budget.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String matchString;
    private String categoryId;

    public TransactionRule(String matchString, String categoryId) {
        this.matchString = matchString;
        this.categoryId = categoryId;
    }

    public TransactionRule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchString() {
        return matchString;
    }

    public void setMatchString(String matchString) {
        this.matchString = matchString;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}