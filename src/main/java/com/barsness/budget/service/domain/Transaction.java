package com.barsness.budget.service.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime transactionDate;
    private String description;
    private String institution;
    private String account;
    private String category;
    private Boolean isHidden;
    private BigDecimal value;
    private String source;
    private LocalDateTime dateCreated;

    public Transaction() {
    }

    public Transaction(LocalDateTime transactionDate, String description, String institution, String account, String category, Boolean isHidden, BigDecimal value, String source) {
        this.transactionDate = transactionDate;
        this.description = description;
        this.institution = institution;
        this.account = account;
        this.category = category;
        this.isHidden = isHidden;
        this.value = value;
        this.source = source;
        this.dateCreated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime tranactionDate) {
        this.transactionDate = tranactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean isHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                ", institution='" + institution + '\'' +
                ", account='" + account + '\'' +
                ", category='" + category + '\'' +
                ", isHidden=" + isHidden +
                ", value=" + value +
                ", source='" + source + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
