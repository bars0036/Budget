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

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Person getPerson(@PathVariable Long id){
            return(personRepo.findById(id));
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Person> getPeople(){
        return personRepo.findAll();
    }



    @RequestMapping(value="/add", method=RequestMethod.POST)
    public Person addBudget(@RequestBody Person person){
        return personRepo.save(person);
    }


}
