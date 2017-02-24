package com.barsness.budget.loader;

import com.barsness.budget.service.domain.Transaction;

import java.util.List;

/**
 * Created by matt.barsness on 2/8/17.
 */
public class LoadResponse {

    private String response;
    private int transLoaded;
    private int transFailed;
    private int transSkipped;
    private List<Transaction> duplicateTransactions;


    public LoadResponse() {
    }

    public LoadResponse(String response, int transLoaded, int transFailed, int transSkipped, List<Transaction> duplicateTransactions) {
        this.response = response;
        this.transLoaded = transLoaded;
        this.transFailed = transFailed;
        this.transSkipped = transSkipped;
        this.duplicateTransactions = duplicateTransactions;
    }

    public List<Transaction> getDuplicateTransactions() {
        return duplicateTransactions;
    }

    public void setDuplicateTransactions(List<Transaction> duplicateTransactions) {
        this.duplicateTransactions = duplicateTransactions;
    }

    public int getTransSkipped() {
        return transSkipped;
    }

    public void setTransSkipped(int transSkipped) {
        this.transSkipped = transSkipped;
    }

    public int getTransLoaded() {
        return transLoaded;
    }

    public void setTransLoaded(int transLoaded) {
        this.transLoaded = transLoaded;
    }

    public int getTransFailed() {
        return transFailed;
    }

    public void setTransFailed(int transFailed) {
        this.transFailed = transFailed;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
