package com.barsness.budget.service.controller;

import com.barsness.budget.service.domain.Person;
import com.barsness.budget.service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonRepository personRepo;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<Person> getBudgets(@RequestParam(value="id") Optional<Long> id){
        if(id.isPresent()){
            List<Person> person = new ArrayList<>();
            person.add(personRepo.findById(id.get()));
            return person;
        }
        return personRepo.findAll();
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public Person addBudget(@RequestBody Person person){
        return personRepo.save(person);
    }


}
