package com.apolis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apolis.model.Customer;
import com.apolis.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(Customer customer) {
        Optional<Customer> optional = customerRepository.findById(customer.getId());
        if (optional.isPresent()) {
            Customer newEntity = optional.get();
            newEntity.setEmail(customer.getEmail());
            newEntity.setName(customer.getName());
            newEntity = customerRepository.save(newEntity);
            return newEntity;
        }
        return null;
    }
}
