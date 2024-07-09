package com.apolis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.apolis.model.Customer;
import com.apolis.model.ErrorDto;
import com.apolis.service.CustomerService;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
		Optional<Customer> result = customerService.findById(id);
		if (result.isPresent()) {
			return new ResponseEntity<>(result.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ErrorDto("Customer with id: " + id + " does not exist!"),
				HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/findAll")
	public ResponseEntity<?> getAllCustomers() {
		List<Customer> result = customerService.findAll();

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
		Customer result = customerService.save(customer);

		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
		Customer result = customerService.updateCustomer(customer);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(new ErrorDto("Customer with id: " + id + " does not exist!"),
				HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		try {
			customerService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorDto(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
