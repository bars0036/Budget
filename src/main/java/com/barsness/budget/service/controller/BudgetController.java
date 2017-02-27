package com.barsness.budget.service.controller;

import com.barsness.budget.service.domain.Budget;
import com.barsness.budget.service.domain.BudgetCategory;
import com.barsness.budget.service.repository.BudgetCategoryRepository;
import com.barsness.budget.service.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    BudgetRepository budgetRepo;

    @Autowired
    BudgetCategoryRepository budgetCategoryRepo;


    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Budget> getBudgets(@RequestParam(value="name") Optional<String> name){
        if(name.isPresent()){
            return budgetRepo.findByName(name.get());
        }
        return budgetRepo.findAll();
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



}
