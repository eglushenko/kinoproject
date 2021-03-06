package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.customer.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPutDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class CustomerServiceTest extends BaseTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;


    private Customer createCustomer() {
        Customer customer = generateFlatEntityWithoutId(Customer.class);
        return customerRepository.save(customer);
    }

    @Test
    public void testGetCustomer() {
        Customer customer = createCustomer();
        CustomerReadDTO customerReadDTO = customerService.getCustomer(customer.getId());
        Assertions.assertThat(customerReadDTO)
                .isEqualToIgnoringGivenFields(customer,
                        "typos", "reviews", "rates", "createdAt", "updatedAt");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCustomerWrongId() {
        customerService.getCustomer(UUID.randomUUID());

    }

    @Test
    public void createCustomerTest() {
        CustomerCreateDTO create = generateObject(CustomerCreateDTO.class);
        CustomerReadDTO customerReadDTO = customerService.createCustomer(create);
        Assertions.assertThat(create)
                .isEqualToIgnoringGivenFields(customerReadDTO,
                        "typos", "reviews", "rates", "createdAt", "updatedAt");
        Assert.assertNotNull(customerReadDTO.getId());

        Customer customer = customerRepository.findById(customerReadDTO.getId()).get();
        Assertions.assertThat(customerReadDTO)
                .isEqualToIgnoringGivenFields(customer,
                        "typos", "reviews", "rates", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchCustomer() {
        Customer customer = createCustomer();

        CustomerPatchDTO patch = generateObject(CustomerPatchDTO.class);
        CustomerReadDTO read = customerService.patchCustomer(customer.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read,
                        "typos", "reviews", "rates", "createdAt", "updatedAt");

        customer = customerRepository.findById(read.getId()).get();
        Assertions.assertThat(customer)
                .isEqualToIgnoringGivenFields(read,
                        "typos", "reviews", "rates", "createdAt", "updatedAt");
    }

    @Test
    public void testPutCustomer() {
        Customer customer = createCustomer();

        CustomerPutDTO put = generateObject(CustomerPutDTO.class);
        CustomerReadDTO read = customerService.updateCustomer(customer.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        customer = customerRepository.findById(read.getId()).get();
        Assertions.assertThat(customer)
                .isEqualToIgnoringGivenFields(read,
                        "typos", "reviews", "rates", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchCustomerEmptyPatch() {
        Customer customer = createCustomer();

        CustomerPatchDTO patch = new CustomerPatchDTO();

        CustomerReadDTO read = customerService.patchCustomer(customer.getId(), patch);

        Assert.assertNotNull(read.getLogin());
        Assert.assertNotNull(read.getFirstName());
        Assert.assertNotNull(read.getLastName());
        Assert.assertNotNull(read.getEmail());
        Assert.assertNotNull(read.getRole());
        Assert.assertNotNull(read.getGender());

        Customer customerAfterUpdate = customerRepository.findById(read.getId()).get();

        Assert.assertNotNull(customerAfterUpdate.getLogin());
        Assert.assertNotNull(customerAfterUpdate.getFirstName());
        Assert.assertNotNull(customerAfterUpdate.getLastName());
        Assert.assertNotNull(customerAfterUpdate.getEmail());
        Assert.assertNotNull(customerAfterUpdate.getRole());
        Assert.assertNotNull(customerAfterUpdate.getGender());

        Assertions.assertThat(customer)
                .isEqualToIgnoringGivenFields(customerAfterUpdate,
                        "typos", "reviews", "rates", "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = createCustomer();

        customerService.deleteCustomer(customer.getId());

        Assert.assertFalse(customerRepository.existsById(customer.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteCustomerNotFoundId() {
        customerService.deleteCustomer(UUID.randomUUID());
    }

}