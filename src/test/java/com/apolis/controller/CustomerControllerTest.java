package com.apolis.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.apolis.model.Customer;
import com.apolis.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetAllCustomers() throws Exception {
        List<Customer> customers = Arrays.asList(new Customer("Gautam", "gautam@gmail.com"),
                new Customer("Aayush", "aayush@gmail.com"));
        when(customerService.findAll()).thenReturn(customers);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/findAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int length = JsonPath.read(result.getResponse().getContentAsString(), "$.length()");
        assertEquals(2, length);
        verify(customerService).findAll();
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer("AddedCustomer", "addedcustomer@gmail.com");
        when(customerService.save(any())).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToString(customer)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        verify(customerService).save(any());
    }

    @Test
    public void testGetCustomerById() throws Exception {

        Customer customer = new Customer(5L, "CustomerById", "customerbyid@gmail.com");
        when(customerService.findById(any())).thenReturn(Optional.of(customer));
        mockMvc.perform(MockMvcRequestBuilders.get("/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToString(customer)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        verify(customerService).findById(any());
    }

    @Test
    public void testUpdateCustomer() throws Exception {

        Customer customer = new Customer(5L, "UpdatedCustomer", "updatedcustomer@gmail.com");
        when(customerService.updateCustomer(any())).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.put("/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToString(customer)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print()).andReturn();

        verify(customerService).updateCustomer(any());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        verify(customerService).deleteById(any());
    }

    private String objToString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
