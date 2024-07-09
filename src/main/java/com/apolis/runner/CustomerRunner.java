package com.apolis.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.apolis.model.Customer;
import com.apolis.repository.CustomerRepository;

@Component
public class CustomerRunner implements CommandLineRunner {

	@Autowired
	CustomerRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		repository.save(new Customer("John","john@gmail.com"));
		repository.save(new Customer("Tom","test@gmail.com"));
		repository.save(new Customer("Ron", "test2@gmail.com"));
	}	
}
