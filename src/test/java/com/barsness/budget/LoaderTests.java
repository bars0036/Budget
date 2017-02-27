package com.barsness.budget;

import com.barsness.budget.loader.LoadResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoaderTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testLoader(){
        ResponseEntity<LoadResponse> forEntity = restTemplate.getForEntity("/load/transactions?fileName={fileName}", LoadResponse.class, "/Users/matt.barsness/Documents/Budget/transactions.csv");
        LoadResponse loadResponse = forEntity.getBody();
        Assert.assertEquals(4426, loadResponse.getTransLoaded());
    }

    @Test
    public void testBudgetSetup(){
        ResponseEntity<String> forEntity = restTemplate.getForEntity("/load/budgetsetup", String.class);
        Assert.assertEquals("Success", forEntity.getBody());
    }

    @Test
    public void testPersonSetup(){
        ResponseEntity<String> forEntity = restTemplate.getForEntity("/load/personsetup", String.class);
        Assert.assertEquals("Success", forEntity.getBody());
    }
}
