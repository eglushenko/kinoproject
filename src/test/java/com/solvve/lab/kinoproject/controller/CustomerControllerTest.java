package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.dto.CustomerReadDTO;
import com.solvve.lab.kinoproject.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetCustomer() throws Exception {
        CustomerReadDTO customer = new CustomerReadDTO();
        customer.setId(UUID.randomUUID());
        customer.setUserName("test");
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


}