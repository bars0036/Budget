package com.barsness.budget;

import com.barsness.budget.service.domain.Budget;
import com.barsness.budget.service.domain.BudgetCategory;
import com.barsness.budget.service.repository.BudgetCategoryRepository;
import com.barsness.budget.service.repository.BudgetRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    BudgetRepository budgetRepo;

    @Autowired
    BudgetCategoryRepository budgetCategoryRepo;

    List<Long> budgetIds = new ArrayList<>();
    List<Long> budgetCategoryIds = new ArrayList<>();

    @Before
    public void before(){
        budgetIds.add(budgetRepo.save(new Budget("Barsness Family Test Budget", "Test Budget")).getId());
        budgetCategoryIds.add(budgetCategoryRepo.save(new BudgetCategory(budgetIds.get(0), "Test Category", "This is a Test Category")).getId());
    }

    @Test
    public void testGetAllBudgets() {
        ResponseEntity<Budget[]> forEntity = restTemplate.getForEntity("/budget/", Budget[].class);
        Budget[] budgets = forEntity.getBody();
        Assert.assertEquals(budgetRepo.count(), budgets.length);
    }

    @Test
    public void testAddBudget(){
        Budget budget = new Budget("Test Add Budget", "Test Budget add");
        HttpEntity<Budget> request = new HttpEntity<>(budget);
        ResponseEntity<Budget> forEntity = restTemplate.exchange("/budget/add/", HttpMethod.POST, request, Budget.class);
        budgetIds.add(forEntity.getBody().getId());
        Assert.assertEquals("Test Budget add", forEntity.getBody().getDescription());
        //Assert.assertEquals(1, forEntity.getBody().getBudgetCategories().size());
    }

    @Test
    public void testGetBudget(){
        ResponseEntity<Budget[]> forEntity = restTemplate.getForEntity("/budget/?name={name}", Budget[].class, "Barsness Family Test Budget");
        Assert.assertEquals(1, forEntity.getBody().length);
    }

    @Test
    public void testAddBudgetCategory(){
        BudgetCategory budgetCategory = new BudgetCategory(budgetIds.get(0), "Home", "Anything for the house");
        HttpEntity<BudgetCategory> request = new HttpEntity<>(budgetCategory);
        ResponseEntity<BudgetCategory> forEntity = restTemplate.exchange("/budget/category/add/", HttpMethod.POST, request, BudgetCategory.class);
        budgetCategoryIds.add(forEntity.getBody().getId());
        Assert.assertEquals("Home", forEntity.getBody().getCategoryName());
    }

    @After
    public void after(){
        for(Long id : budgetIds){
            budgetRepo.delete(id);
        }
        for(Long id : budgetCategoryIds){
            budgetCategoryRepo.delete(id);
        }
    }
}
