package com.barsness.budget.loader;


import com.barsness.budget.service.domain.Transaction;
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

    @RequestMapping(value = "/run", method= RequestMethod.GET)
    public LoadResponse loadTransactions(@RequestParam String fileName){
        List<Transaction> trans = ParseTransactionCSV.parseTransactionCSV(fileName);
        List<Transaction> duplicateTrans = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        int transLoaded = 0;
        int transFailed = 0;
        int transSkipped = 0;
        for (Transaction tran : trans){
            ResponseEntity<Transaction[]> checkExistsResponse = restTemplate.getForEntity("http://localhost:8080/transaction/find?transDate={transDate}&description={description}&value={value}", Transaction[].class, tran.getTransactionDate().toString(), tran.getDescription(), tran.getValue());
            if(checkExistsResponse.getBody().length == 0) {
                HttpEntity<Transaction> request = new HttpEntity<>(tran);
                ResponseEntity<Transaction> forEntity = restTemplate.exchange("http://localhost:8080/transaction/add", HttpMethod.POST, request, Transaction.class);
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

}
