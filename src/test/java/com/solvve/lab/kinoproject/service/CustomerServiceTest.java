package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.CustomerReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from customer", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerServiceTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    public void getCustomerTest() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setFirstName("Jhon");
        customer.setLastName("Dou");
        customer = customerRepository.save(customer);

        CustomerReadDTO customerReadDTO = customerService.getCustomer(customer.getId());
        Assertions.assertThat(customerReadDTO).isEqualToComparingFieldByField(customer);

    }

    @Test(expected = EntityNotFoundException.class)
    public void getCustomerWrongId() {
        customerService.getCustomer(UUID.randomUUID());

    }


}