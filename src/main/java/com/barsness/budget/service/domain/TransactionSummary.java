package com.barsness.budget.service.domain;

/**
 * Created by matt.barsness on 2/9/17.
 */
public class TransactionSummary {


    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public TransactionSummary(long count) {

        this.count = count;
    }

    public TransactionSummary() {

    }
}
