package com.barsness.budget.loader;


import com.barsness.budget.service.domain.Budget;
import com.barsness.budget.service.domain.BudgetCategory;
import com.barsness.budget.service.domain.Transaction;
import com.barsness.budget.service.repository.BudgetCategoryRepository;
import com.barsness.budget.service.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/load")
public class LoaderController {

    String budgetAppUrl = "http://localhost:8080";
    private static boolean setupBudget = false;

    @Autowired
    BudgetRepository budgetRepo;

    @Autowired
    BudgetCategoryRepository budgetCatRepo;

    @RequestMapping(value = "/transactions", method= RequestMethod.GET)
    public LoadResponse loadTransactions(@RequestParam String fileName){
        List<Transaction> trans = ParseTransactionCSV.parseTransactionCSV(fileName);
        List<Transaction> duplicateTrans = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        int transLoaded = 0;
        int transFailed = 0;
        int transSkipped = 0;
        for (Transaction tran : trans){
            ResponseEntity<Transaction[]> checkExistsResponse = restTemplate.getForEntity(budgetAppUrl+"/transaction/find?transDate={transDate}&description={description}&value={value}", Transaction[].class, tran.getTransactionDate().toString(), tran.getDescription(), tran.getValue());
            if(checkExistsResponse.getBody().length == 0) {
                HttpEntity<Transaction> request = new HttpEntity<>(tran);
                ResponseEntity<Transaction> forEntity = restTemplate.exchange(budgetAppUrl+"/transaction/add", HttpMethod.POST, request, Transaction.class);
                if (forEntity.getStatusCode() == HttpStatus.OK) {
                    transLoaded++;
                } else {
                    transFailed++;
                }
            }
            else{
                duplicateTrans.add(tran);
                transSkipped++;
                System.out.println("Transaction Not Loaded: " + tran.toString());
            }
        }
        return new LoadResponse("Success", transLoaded, transFailed, transSkipped, duplicateTrans);
    }


    @RequestMapping(value="/budgetsetup", method=RequestMethod.GET)
    public String budgetSetup(){
        if (setupBudget) {
            Budget budget = new Budget("Barsness Family", "First Barsness Family Budget");
            budget = budgetRepo.save(budget);
            BudgetCategory home = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Home", "Anything For the Home"));
            BudgetCategory mortgage = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Mortgage", "All Mortgages", home.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "First Mortgage", "Main Mortgage", mortgage.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Second Mortgage", "HELOC", mortgage.getId()));
            BudgetCategory utilities = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Utilities", "Home Utilitites",home.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Garbage and Recycling", "Garbage and Recycling", utilities.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Gas", "Gas", utilities.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Electricity", "Electricity", utilities.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Water and Sewer", "Water and Sewer", utilities.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Home Improvement", "Home Improvement", home.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Home Maintenance", "Home Maintenance", home.getId()));
            BudgetCategory car = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Car", "Cars"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Car Payment", "Car Payment", car.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Car Insurance", "Car Insurance", car.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Gas", "Gas", car.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Car Repairs", "Car Repairs", car.getId()));
            BudgetCategory shopping = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Shopping", "Shopping"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Groceries", "Groceries", shopping.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Clothes", "Clothes", shopping.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Groceries", "Groceries", shopping.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Electronics", "Electronics", shopping.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Sports Equipment", "Sports Equipment", shopping.getId()));
            BudgetCategory entertainment = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Entertainment", "Entertainment"));
            BudgetCategory tv =budgetCatRepo.save(new BudgetCategory(budget.getId(), "TV", "TV, Internet, and Streaming", entertainment.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Cable and Internet", "Cable and Internet", tv.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Streaming Services", "Streaming Services", tv.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Movies", "Movies", entertainment.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Music", "Music", entertainment.getId()));
            BudgetCategory sportingevents = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Groceries", "Groceries", entertainment.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Season Tickets", "Season Tickets", sportingevents.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Other", "Other Sporting Events", sportingevents.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Concerts", "Concerts", entertainment.getId()));
            BudgetCategory food = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Food", "Food and Groceries"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Groceries", "Groceries", food.getId()));
            BudgetCategory restaurants = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Restaurants", "Groceries", food.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Restaurants", "Restaurants", restaurants.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Lunch", "Lunch", restaurants.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Take out", "Take out", restaurants.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Business Lunch Non-Reimbursable", "Business Lunch Non-Reimbursable", restaurants.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Business Lunch Reimbursable", "Business Lunch Reimbursable", restaurants.getId()));
            BudgetCategory insurance = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Insurance", "Insurance"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Life Insurance", "Life Insurance", insurance.getId()));
            BudgetCategory travel = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Travel", "Travel"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Flights", "Flights", travel.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Hotels", "Hotels", travel.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Rental Car", "Rental Car", travel.getId()));
            BudgetCategory fitness = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Sports and Fitness", "Sports and Fitness"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Golf", "Golf", fitness.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Health Club", "Health Club", fitness.getId()));
            BudgetCategory health = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Health and Personal Care", "Health and Personal Care"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Hair", "Hair", health.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Nails", "Nails", health.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Spa and Massage", "Spa and Massage", health.getId()));
            BudgetCategory medical = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Medical", "Medical"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Medical", "Medical", medical.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Dentist", "Dentist", medical.getId()));
            BudgetCategory kids = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Kids", "Kids Expenses"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Daycare", "Daycare", kids.getId()));
            BudgetCategory invest = budgetCatRepo.save(new BudgetCategory(budget.getId(), "Investments", "Investments"));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "College", "College", invest.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Retirement", "Retirement", invest.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Short Term Savings", "Short Term Savings", invest.getId()));
            budgetCatRepo.save(new BudgetCategory(budget.getId(), "Unknown", "Unknown"));

        }
        return "Success";
    }
}
