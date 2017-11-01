package com.barsness.budget.loader;


import com.barsness.budget.service.domain.Budget;
import com.barsness.budget.service.domain.BudgetCategory;
import com.barsness.budget.service.domain.Person;
import com.barsness.budget.service.domain.Transaction;
import com.barsness.budget.service.repository.BudgetCategoryRepository;
import com.barsness.budget.service.repository.BudgetRepository;
import com.barsness.budget.service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${budget.budgetloader.setupBudget}")
    private boolean setupBudget;

    @Value("${budget.budgetloader.setupPerson}")
    private boolean setupPerson;

    @Autowired
    BudgetRepository budgetRepo;

    @Autowired
    PersonRepository personRepo;

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

            BudgetCategory car = new BudgetCategory("Car", "Cars");
            car.addBudgetCategory(new BudgetCategory("Car Payment", "Car Payment"));
            car.addBudgetCategory(new BudgetCategory( "Car Insurance", "Car Insurance"));
            car.addBudgetCategory(new BudgetCategory( "Gas", "Gas"));
            car.addBudgetCategory(new BudgetCategory( "Car Repairs", "Car Repairs"));

            budget.getBudgetCategories().add(car);
                

            BudgetCategory home = new BudgetCategory("Home", "Anything For the Home");
            BudgetCategory mortgage = new BudgetCategory("Mortgage", "All Mortgages");
            mortgage.addBudgetCategory(new BudgetCategory("First Mortgage", "Main Mortgage"));
            mortgage.addBudgetCategory(new BudgetCategory("Second Mortgage", "HELOC"));
            home.addBudgetCategory(mortgage);

            BudgetCategory utilities = new BudgetCategory("Utilities", "Home Utilitites");
            utilities.addBudgetCategory(new BudgetCategory("Garbage and Recycling", "Garbage and Recycling"));
            utilities.addBudgetCategory(new BudgetCategory("Gas", "Gas"));
            utilities.addBudgetCategory(new BudgetCategory("Electricity", "Electricity"));
            utilities.addBudgetCategory(new BudgetCategory("Water and Sewer", "Water and Sewer"));
            home.addBudgetCategory(utilities);

            home.addBudgetCategory(new BudgetCategory("Home Improvement", "Home Improvement"));
            home.addBudgetCategory(new BudgetCategory("Home Maintenance", "Home Maintenance"));
            budget.getBudgetCategories().add(home);
            /*
            BudgetCategory shopping = budgetCatRepo.save(new BudgetCategory("Shopping", "Shopping"));
            budgetCatRepo.save(new BudgetCategory("Groceries", "Groceries", shopping.getId()));
            budgetCatRepo.save(new BudgetCategory("Clothes", "Clothes", shopping.getId()));
            budgetCatRepo.save(new BudgetCategory("Groceries", "Groceries", shopping.getId()));
            budgetCatRepo.save(new BudgetCategory("Electronics", "Electronics", shopping.getId()));
            budgetCatRepo.save(new BudgetCategory("Sports Equipment", "Sports Equipment", shopping.getId()));
            BudgetCategory entertainment = budgetCatRepo.save(new BudgetCategory("Entertainment", "Entertainment"));
            BudgetCategory tv =budgetCatRepo.save(new BudgetCategory("TV", "TV, Internet, and Streaming", entertainment.getId()));
            budgetCatRepo.save(new BudgetCategory("Cable and Internet", "Cable and Internet", tv.getId()));
            budgetCatRepo.save(new BudgetCategory("Streaming Services", "Streaming Services", tv.getId()));
            budgetCatRepo.save(new BudgetCategory("Movies", "Movies", entertainment.getId()));
            budgetCatRepo.save(new BudgetCategory("Music", "Music", entertainment.getId()));
            BudgetCategory sportingevents = budgetCatRepo.save(new BudgetCategory("Groceries", "Groceries", entertainment.getId()));
            budgetCatRepo.save(new BudgetCategory("Season Tickets", "Season Tickets", sportingevents.getId()));
            budgetCatRepo.save(new BudgetCategory("Other", "Other Sporting Events", sportingevents.getId()));
            budgetCatRepo.save(new BudgetCategory("Concerts", "Concerts", entertainment.getId()));
            BudgetCategory food = budgetCatRepo.save(new BudgetCategory("Food", "Food and Groceries"));
            budgetCatRepo.save(new BudgetCategory("Groceries", "Groceries", food.getId()));
            BudgetCategory restaurants = budgetCatRepo.save(new BudgetCategory("Restaurants", "Groceries", food.getId()));
            budgetCatRepo.save(new BudgetCategory("Restaurants", "Restaurants", restaurants.getId()));
            budgetCatRepo.save(new BudgetCategory("Lunch", "Lunch", restaurants.getId()));
            budgetCatRepo.save(new BudgetCategory("Take out", "Take out", restaurants.getId()));
            budgetCatRepo.save(new BudgetCategory("Business Lunch Non-Reimbursable", "Business Lunch Non-Reimbursable", restaurants.getId()));
            budgetCatRepo.save(new BudgetCategory("Business Lunch Reimbursable", "Business Lunch Reimbursable", restaurants.getId()));
            BudgetCategory insurance = budgetCatRepo.save(new BudgetCategory("Insurance", "Insurance"));
            budgetCatRepo.save(new BudgetCategory("Life Insurance", "Life Insurance", insurance.getId()));
            BudgetCategory travel = budgetCatRepo.save(new BudgetCategory("Travel", "Travel"));
            budgetCatRepo.save(new BudgetCategory("Flights", "Flights", travel.getId()));
            budgetCatRepo.save(new BudgetCategory("Hotels", "Hotels", travel.getId()));
            budgetCatRepo.save(new BudgetCategory("Rental Car", "Rental Car", travel.getId()));
            BudgetCategory fitness = budgetCatRepo.save(new BudgetCategory("Sports and Fitness", "Sports and Fitness"));
            budgetCatRepo.save(new BudgetCategory("Golf", "Golf", fitness.getId()));
            budgetCatRepo.save(new BudgetCategory("Health Club", "Health Club", fitness.getId()));
            BudgetCategory health = budgetCatRepo.save(new BudgetCategory("Health and Personal Care", "Health and Personal Care"));
            budgetCatRepo.save(new BudgetCategory("Hair", "Hair", health.getId()));
            budgetCatRepo.save(new BudgetCategory("Nails", "Nails", health.getId()));
            budgetCatRepo.save(new BudgetCategory("Spa and Massage", "Spa and Massage", health.getId()));
            BudgetCategory medical = budgetCatRepo.save(new BudgetCategory("Medical", "Medical"));
            budgetCatRepo.save(new BudgetCategory("Medical", "Medical", medical.getId()));
            budgetCatRepo.save(new BudgetCategory("Dentist", "Dentist", medical.getId()));
            BudgetCategory kids = budgetCatRepo.save(new BudgetCategory("Kids", "Kids Expenses"));
            budgetCatRepo.save(new BudgetCategory("Daycare", "Daycare", kids.getId()));
            BudgetCategory invest = budgetCatRepo.save(new BudgetCategory("Investments", "Investments"));
            budgetCatRepo.save(new BudgetCategory("College", "College", invest.getId()));
            budgetCatRepo.save(new BudgetCategory("Retirement", "Retirement", invest.getId()));
            budgetCatRepo.save(new BudgetCategory("Short Term Savings", "Short Term Savings", invest.getId()));
            budgetCatRepo.save(new BudgetCategory("Unknown", "Unknown"));*/
            budgetRepo.save(budget);
        }
        return "Success2";
    }

    @RequestMapping(value="/personsetup", method=RequestMethod.GET)
    public String personSetup() {
        if(setupPerson){
            personRepo.save(new Person("Matt", "Barsness", "bars0036@gmail.com", "612-986-2973"));
            personRepo.save(new Person("Julie", "Barsness", "hoolie814@yahoo.com", "612-220-6577"));
            personRepo.save(new Person("Teagan", "Barsness"));
            personRepo.save(new Person("Anders", "Barsness"));
        }
        return "Success";
    }
}
