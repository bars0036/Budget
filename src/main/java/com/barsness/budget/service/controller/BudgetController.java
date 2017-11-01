package com.barsness.budget.service.controller;

import com.barsness.budget.service.domain.Budget;
import com.barsness.budget.service.domain.BudgetCategory;
import com.barsness.budget.service.domain.BudgetTransaction;
import com.barsness.budget.service.repository.BudgetCategoryRepository;
import com.barsness.budget.service.repository.BudgetRepository;
import com.barsness.budget.service.repository.BudgetTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    BudgetRepository budgetRepo;

    @Autowired
    BudgetCategoryRepository budgetCategoryRepo;

    @Autowired
    BudgetTransactionRepository budgetTransactionRepo;


    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Budget> getBudgets(@RequestParam(value="name") Optional<String> name){
        if(name.isPresent()){
            return budgetRepo.findByName(name.get());
        }
        return budgetRepo.findAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Budget getBudgets(@PathVariable Long id){
        return budgetRepo.findById(id);
    }
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public Budget addBudget(@RequestBody Budget budget){
        return budgetRepo.save(budget);
    }

    @RequestMapping(value="/category/add", method= RequestMethod.POST)
    public BudgetCategory addBudgetCategory(@RequestBody BudgetCategory budgetCategory){
        return budgetCategoryRepo.save(budgetCategory);
    }

    @RequestMapping(value="/category", method = RequestMethod.GET)
    public List<BudgetCategory> allBudgetCategories(){
        return budgetCategoryRepo.findAll();
    }


    @RequestMapping(value="/transaction", method = RequestMethod.GET)
    public List<BudgetTransaction> allBudgetTransactions(){return budgetTransactionRepo.findAll();}

    @RequestMapping(value="/transaction/add", method= RequestMethod.POST)
    public BudgetTransaction addBudgetTransaction(@RequestBody BudgetTransaction budgetTransaction){
        return budgetTransactionRepo.save(budgetTransaction);
    }

    @RequestMapping(value="/category/{id}/assign-transaction", method=RequestMethod.PUT)
    public BudgetTransaction assignTransaction(@PathVariable Long id, @RequestParam Long transactionId, @RequestParam Long personId, @RequestParam BigDecimal value){
        BudgetTransaction bt = new BudgetTransaction(id, transactionId, personId, value);
        bt = budgetTransactionRepo.save(bt);
        return bt;
    }

}
