package com.apolis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.apolis.model.Customer;
import com.apolis.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testFindAll() {
        when(customerRepository.findAll())
                .thenReturn(Arrays.asList(new Customer("Gautam", "gautam@gmail.com"),
                        new Customer("Aayush", "aayush@gmail.com")));
        List<Customer> customers = customerService.findAll();
        assertEquals(2, customers.size());

    }

    @Test
    public void testFindById() {
        Customer customer = new Customer(5L, "CustomerById", "customerbyid@gmail.com");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        assertEquals(customerService.findById(1L).get().getId(), customer.getId());
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer("AddedCustomer", "addedcustomer@gmail.com");
        when(customerRepository.save(any())).thenReturn(customer);
        customerService.save(customer);
        verify(customerRepository).save(any());

    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer(5L, "UpdatedCustomer", "updatedcustomer@gmail.com");
        when(customerRepository.findById(5L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        assertEquals(customer.getId(), updatedCustomer.getId());
        verify(customerRepository).findById(5L);
        verify(customerRepository).save(customer);
    }

    @Test
    public void testDeleteCustomer() {
        customerService.deleteById(5L);
        verify(customerRepository).deleteById(5L);
    }
}
