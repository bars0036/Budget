package com.barsness.budget.service.repository;


import com.barsness.budget.service.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>{
    Person findById(Long id);
}
