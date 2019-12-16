package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from customer", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer = customerRepository.save(customer);
        assertNotNull(customer.getId());
        assertTrue(customerRepository.findById(customer.getId()).isPresent());
    }
}