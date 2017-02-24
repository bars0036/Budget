package com.barsness.budget.service.controller;

import com.barsness.budget.service.domain.Transaction;
import com.barsness.budget.service.domain.TransactionSummary;
import com.barsness.budget.service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionRepository tranRepo;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<Transaction> findAll(){
        return tranRepo.findAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Transaction findTransactionById(@PathVariable Long id){
        Transaction trans = tranRepo.findById(id);
        return trans;
    }
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public Transaction updateRecord(@RequestBody Transaction transaction){
        return tranRepo.save(transaction);
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public Transaction addRecord(@RequestBody Transaction transaction){
        return tranRepo.save(transaction);
    }

    @RequestMapping(value="/summary", method=RequestMethod.GET)
    public TransactionSummary getTransactionSummary(){
        return new TransactionSummary(tranRepo.count());
    }

    @RequestMapping(value="/find", method=RequestMethod.GET)
    public List<Transaction> findTransactions(@RequestParam String transDate,
                                              @RequestParam String description, @RequestParam BigDecimal value) {
        return tranRepo.findByTransactionDateAndDescriptionAndValue(LocalDateTime.parse(transDate), description, value);
    }
}
