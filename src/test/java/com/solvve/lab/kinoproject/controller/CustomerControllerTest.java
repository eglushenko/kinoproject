package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.CustomerReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    public void getCustomerTest() throws Exception {
        CustomerReadDTO customer = new CustomerReadDTO();
        customer.setId(UUID.randomUUID());
        customer.setLogin("test");
        customer.setFirstName("John");
        customer.setLastName("Dou");
        customer.setEmail("test@mail.ua");

        Mockito.when(customerService.getCustomer(customer.getId())).thenReturn(customer);

        String resultJson = mvc.perform(get("/api/v1/customers/{id}", customer.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(resultJson);
        CustomerReadDTO actualDTO = objectMapper.readValue(resultJson, CustomerReadDTO.class);
        Assertions.assertThat(actualDTO).isEqualToComparingFieldByField(customer);

        Mockito.verify(customerService).getCustomer(customer.getId());

    }

    @Test
    public void getCustomerWrongIdTest() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Customer.class, wrongId);
        Mockito.when(customerService.getCustomer(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/customers/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void getCustomerWrongUUIDFormatTest() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/customers/1234"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void getCustomerErrorFormatTest() throws Exception {
        String resultJson = mvc.perform(get("/api/v1/customers/1234"))
                .andReturn().getResponse().getContentAsString();

        Set<String> testResult = new HashSet<>();
        testResult.add("status");
        testResult.add("exeptionClass");
        testResult.add("msg");

        ObjectReader reader = new ObjectMapper().readerFor(Map.class);
        HashMap<String, String> map = reader.readValue(resultJson);
        /* Equals only kay */
        Assert.assertEquals("Json format not equals", map.keySet(), testResult);
    }

    @Test
    public void createCustomerTest() throws Exception {
        CustomerCreateDTO create = new CustomerCreateDTO();
        create.setLogin("user");
        create.setFirstName("Jhon");
        create.setLastName("Dou");
        create.setEmail("mail@mail.ua");

        CustomerReadDTO read = new CustomerReadDTO();
        read.setId(UUID.randomUUID());
        read.setLogin("user");
        read.setFirstName("Jhon");
        read.setLastName("Dou");
        read.setEmail("mail@mail.ua");

        Mockito.when(customerService.createCustomer(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/customers")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CustomerReadDTO customerReadDTO = objectMapper.readValue(resultJson, CustomerReadDTO.class);
        Assertions.assertThat(customerReadDTO).isEqualToComparingFieldByField(read);
    }

}