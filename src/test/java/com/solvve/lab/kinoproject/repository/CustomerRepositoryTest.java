package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CustomerRepositoryTest extends BaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer createCustomer() {
        Customer customer = generateFlatEntityWithoutId(Customer.class);
        return customerRepository.save(customer);
    }

    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer = customerRepository.save(customer);
        assertNotNull(customer.getId());
        assertTrue(customerRepository.findById(customer.getId()).isPresent());
    }

    @Test
    public void testCustomerCreateDate() {
        Customer customer = createCustomer();
        Assert.assertNotNull(customer.getCreatedAt());

        Instant createDateBeforeLoad = customer.getCreatedAt();

        customer = customerRepository.findById(customer.getId()).get();

        Instant createDateAfterLoad = customer.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test
    public void testCustomerUpdateDate() {
        Customer customer = createCustomer();
        Assert.assertNotNull(customer.getUpdatedAt());

        Instant updateDateBeforeLoad = customer.getUpdatedAt();

        customer.setRole(Role.GUEST);
        customer = customerRepository.save(customer);
        customer = customerRepository.findById(customer.getId()).get();

        Instant updateDateAfterLoad = customer.getUpdatedAt();

        Assert.assertNotNull(updateDateAfterLoad);
        Assertions.assertThat(updateDateAfterLoad).isAfter(updateDateBeforeLoad);
    }
}