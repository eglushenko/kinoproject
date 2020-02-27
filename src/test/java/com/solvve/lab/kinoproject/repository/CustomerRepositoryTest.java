package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.enums.Gender;
import com.solvve.lab.kinoproject.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from customer", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setLogin("user");
        customer.setFirstName("Jhon");
        customer.setLastName("Dou");
        customer.setEmail("mail@mail.ua");
        customer.setRole(Role.USER);
        customer.setGender(Gender.MALE);
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