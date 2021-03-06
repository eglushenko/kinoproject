package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.customer.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPutDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest extends BaseControllerTest {

    @MockBean
    private CustomerService customerService;

    private CustomerReadDTO createCustomerRead() {
        return generateObject(CustomerReadDTO.class);
    }

    @Test
    public void testGetCustomer() throws Exception {
        CustomerReadDTO customer = createCustomerRead();

        Mockito.when(customerService.getCustomer(customer.getId())).thenReturn(customer);

        String resultJson = mvc.perform(get("/api/v1/customers/{id}", customer.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(resultJson);
        CustomerReadDTO actualDTO = objectMapper.readValue(resultJson, CustomerReadDTO.class);
        Assertions.assertThat(actualDTO).isEqualToComparingFieldByField(customer);

        Mockito.verify(customerService).getCustomer(customer.getId());

    }

    @Test
    public void testGetCustomerWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Customer.class, wrongId);
        Mockito.when(customerService.getCustomer(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/customers/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetCustomerWrongUUIDFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/customers/1234"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testGetCustomerErrorFormat() throws Exception {
        String resultJson = mvc.perform(get("/api/v1/customers/1234"))
                .andReturn().getResponse().getContentAsString();

        Set<String> testResult = new HashSet<>();
        testResult.add("status");
        testResult.add("exeptionClass");
        testResult.add("msg");

        ObjectReader reader = new ObjectMapper().readerFor(Map.class);
        HashMap<String, String> map = reader.readValue(resultJson);
        Assert.assertEquals(map.keySet(), testResult);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerCreateDTO create = generateObject(CustomerCreateDTO.class);
        CustomerReadDTO read = createCustomerRead();

        Mockito.when(customerService.createCustomer(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/customers")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CustomerReadDTO customerReadDTO = objectMapper.readValue(resultJson, CustomerReadDTO.class);
        Assertions.assertThat(customerReadDTO)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerPatchDTO patchDTO = generateObject(CustomerPatchDTO.class);
        CustomerReadDTO read = createCustomerRead();

        Mockito.when(customerService.patchCustomer(read.getId(), patchDTO)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/customers/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CustomerReadDTO actualCustomer = objectMapper.readValue(resultJson, CustomerReadDTO.class);
        Assert.assertEquals(read, actualCustomer);
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/customers/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(customerService).deleteCustomer(id);
    }

    @Test
    public void testPutCustomer() throws Exception {
        CustomerPutDTO putDTO = generateObject(CustomerPutDTO.class);
        CustomerReadDTO read = createCustomerRead();

        Mockito.when(customerService.updateCustomer(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/customers/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CustomerReadDTO actualCustomer = objectMapper.readValue(resultJson, CustomerReadDTO.class);
        Assert.assertEquals(read, actualCustomer);
    }


}