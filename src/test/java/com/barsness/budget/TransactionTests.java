package com.barsness.budget;

import com.barsness.budget.service.domain.Transaction;
import com.barsness.budget.service.repository.TransactionRepository;
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
public class TransactionTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    TransactionRepository repo;



    List<Long> transIds = new ArrayList();


    @Before
    public void setupTestData(){
        transIds.add(repo.save(new Transaction(LocalDateTime.parse("2017-01-02T00:00:00"), "Test 1", "US Bank", "1111", "Fun", new Boolean(false), BigDecimal.valueOf(1.01), "Test")).getId());
        transIds.add(repo.save(new Transaction(LocalDateTime.parse("2017-01-02T00:00:00"), "Test 2", "Amex", "2222", "Groceries", new Boolean(false), BigDecimal.valueOf(2.01), "Test")).getId());
        transIds.add(repo.save(new Transaction(LocalDateTime.parse("2017-01-10T00:00:00"), "Test 3", "US Bank", "3333", "House", new Boolean(false), BigDecimal.valueOf(3.01), "Test")).getId());
        transIds.add(repo.save(new Transaction(LocalDateTime.parse("2017-01-22T00:00:00"), "Test 4", "Amex", "4444", "Stuff", new Boolean(false), BigDecimal.valueOf(4.01), "Test")).getId());
    }

    @Test
    public void testGetAllTransactions(){
        ResponseEntity<Transaction[]> forEntity = restTemplate.getForEntity("/transaction/", Transaction[].class);
        Transaction[] trans = forEntity.getBody();
        Assert.assertEquals(repo.count(), trans.length);

    }

    @Test
    public void testGetOneTransaction(){
        ResponseEntity<Transaction> forEntity = restTemplate.getForEntity("/transaction/{id}", Transaction.class, transIds.get(0));
        Transaction trans = forEntity.getBody();
        Assert.assertEquals("Test 1", trans.getDescription());
    }

    @After
    public void destroyTestData(){
        for(Long id : transIds){
            repo.delete(id);
        }
    }

    @Test
    public void testUpdateTransactions(){
        ResponseEntity<Transaction> getTranEntity = restTemplate.getForEntity("/transaction/{id}", Transaction.class, transIds.get(0));
        Transaction transaction = getTranEntity.getBody();
        transaction.setDescription("Executed Test");
        HttpEntity<Transaction> request = new HttpEntity<>(transaction);
        ResponseEntity<Transaction> forEntity = restTemplate.exchange("/transaction/update/", HttpMethod.POST, request, Transaction.class);
        Transaction responseTrans = forEntity.getBody();
        Assert.assertEquals("Executed Test", responseTrans.getDescription());
    }

    @Test
    public void testAddTransaction(){
        Transaction trans = new Transaction(LocalDateTime.now(), "Test Add Transaction",
                "US Bank", "1111", "Fun", new Boolean(false),
                BigDecimal.valueOf(1.01), "Test");
        HttpEntity<Transaction> request = new HttpEntity<>(trans);
        ResponseEntity<Transaction> forEntity = restTemplate.exchange("/transaction/add/", HttpMethod.POST, request, Transaction.class);
        transIds.add(forEntity.getBody().getId());
        Assert.assertEquals("Test Add Transaction", forEntity.getBody().getDescription());
    }

    @Test
    public void testFindTransaction(){
        ResponseEntity<Transaction[]> forEntity = restTemplate.getForEntity("/transaction/find?transDate={transDate}&description={description}&value={value}", Transaction[].class, "2017-01-02T00:00:00", "Test 1", "1.01");
        Transaction[] trans = forEntity.getBody();
        Assert.assertEquals(1, trans.length);
        Assert.assertEquals("Fun", trans[0].getCategory());
    }

}
