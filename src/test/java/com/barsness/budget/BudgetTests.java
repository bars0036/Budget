package com.barsness.budget;

import com.barsness.budget.service.domain.Budget;
import com.barsness.budget.service.domain.BudgetCategory;
import com.barsness.budget.service.domain.BudgetTransaction;
import com.barsness.budget.service.domain.Person;
import com.barsness.budget.service.repository.BudgetCategoryRepository;
import com.barsness.budget.service.repository.BudgetRepository;
import com.barsness.budget.service.repository.BudgetTransactionRepository;
import com.barsness.budget.service.repository.PersonRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Autowired
    BudgetTransactionRepository budgetTransactionRepo;

    @Autowired
    PersonRepository personRepo;

    List<Long> budgetIds = new ArrayList<>();
    List<Long> budgetCategoryIds = new ArrayList<>();
    List<Long> budgetTransactionIds = new ArrayList<>();
    List<Long> personIds = new ArrayList<>();


    @Before
    public void before(){
        budgetIds.add(budgetRepo.save(new Budget("Barsness Family Test Budget", "Test Budget")).getId());
        budgetCategoryIds.add(budgetCategoryRepo.save(new BudgetCategory(budgetIds.get(0), "Test Category", "This is a Test Category")).getId());
        personIds.add(personRepo.save(new Person("Test", "User 1", "bars0036@yahoo.com", "612-986-2206")).getId());
        personIds.add(personRepo.save(new Person("Test", "User 2", "bars0035@yahoo.com", "612-220-9862")).getId());
        budgetTransactionIds.add(budgetTransactionRepo.save(new BudgetTransaction(budgetCategoryIds.get(0), new Long(35), personIds.get(0), LocalDateTime.now(), new BigDecimal(26.96))).getId());

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

    @Test
    public void testGetBudgetTransaction(){
        ResponseEntity<BudgetTransaction[]> forEntity = restTemplate.getForEntity("/budget/transaction/", BudgetTransaction[].class);
        Assert.assertEquals(budgetTransactionRepo.count(), forEntity.getBody().length);
    }

    @Test
    public void testAddBudgetTransaction(){
        BudgetTransaction bt = new BudgetTransaction(budgetCategoryIds.get(0), new Long(22), personIds.get(0), LocalDateTime.now(), new BigDecimal(79.83));
        HttpEntity<BudgetTransaction> request = new HttpEntity<>(bt);
        ResponseEntity<BudgetTransaction> forEntity = restTemplate.exchange("/budget/transaction/add/", HttpMethod.POST, request, BudgetTransaction.class);
        budgetTransactionIds.add(forEntity.getBody().getId());
        Assert.assertEquals(bt.getTransactionId(), forEntity.getBody().getTransactionId());
    }

    @Test
    public void testGetAllPeople(){
        ResponseEntity<Person[]> forEntity = restTemplate.getForEntity("/person/", Person[].class);
        Assert.assertEquals(personRepo.count(), forEntity.getBody().length);
    }

    @Test
    public void testGetPersonById(){
        ResponseEntity<Person[]> forEntity = restTemplate.getForEntity("/person/?id={id}", Person[].class, personIds.get(0));
        Assert.assertEquals("User 1", forEntity.getBody()[0].getLastName());
    }

    @Test
    public void testAddPerson(){
        Person person = new Person("Tester", "UserInTest", "test@test.com", "61289797124");
        HttpEntity<Person> request = new HttpEntity<>(person);
        ResponseEntity<Person> forEntity = restTemplate.exchange("/person/add/", HttpMethod.POST, request, Person.class);
        personIds.add(forEntity.getBody().getId());
        Assert.assertEquals("test@test.com", forEntity.getBody().getEmailAddress());
    }

    @After
    public void after(){
        for(Long id : budgetIds){
            budgetRepo.delete(id);
        }
        for(Long id : budgetCategoryIds){
            budgetCategoryRepo.delete(id);
        }
        for(Long id : budgetTransactionIds){
            budgetTransactionRepo.delete(id);
        }
        for (Long id : personIds){
            personRepo.delete(id);
        }
    }
}
