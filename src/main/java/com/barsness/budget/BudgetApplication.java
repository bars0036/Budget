package com.barsness.budget;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BudgetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetApplication.class, args);
	}

	@Bean
	public Jdk8Module jdk8Module() {
		return new Jdk8Module();
	}

	@Bean
	public JavaTimeModule javaTimeModule(){
		return new JavaTimeModule();
	}
}
