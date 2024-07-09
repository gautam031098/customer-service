package com.apolis.repository;

import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.apolis.model.Customer;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindAll() {
        List<Customer> customers = customerRepository.findAll();
        assertEquals(2, customers.size());
    }

    @Test
    public void testFindOne() {
        Customer customer = customerRepository.findById(1L).get();
        assertEquals("Test1", customer.getName());
    }

    @Test
    public void testAddProduct() {
        Customer customer = new Customer( "AddedCustomer", "addedcustomer@gmail.com");
        customerRepository.save(customer);
        Optional<Customer> result = customerRepository.findById(customer.getId());
        Customer fetchedCustomer = result.get();
        assertEquals(customer.getId(), fetchedCustomer.getId());

    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer( "UpdatedCustomer", "updatedcustomer@gmail.com");
        customerRepository.save(customer);
        Optional<Customer> result = customerRepository.findById(customer.getId());
        Customer updatedCustomer = result.get();
        assertEquals(customer.getId(), updatedCustomer.getId());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer("CustomerToDelete", "customertodelete@gmail.com");
        customerRepository.save(customer);
        customerRepository.deleteById(customer.getId());
        Optional<Customer> result = customerRepository.findById(customer.getId());
        assertEquals(Optional.empty(), result);
    }
}
