package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.CustomerReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
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

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setLogin("user");
        customer.setFirstName("Jhon");
        customer.setLastName("Dou");
        customer.setEmail("mail@mail.ua");
        return customerRepository.save(customer);
    }

    @Test
    public void testGetCustomer() {
        Customer customer = createCustomer();
        CustomerReadDTO customerReadDTO = customerService.getCustomer(customer.getId());
        Assertions.assertThat(customerReadDTO).isEqualToComparingFieldByField(customer);

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetCustomerWrongId() {
        customerService.getCustomer(UUID.randomUUID());

    }

    @Test
    public void createCustomerTest() {
        CustomerCreateDTO create = new CustomerCreateDTO();
        create.setLogin("user");
        create.setFirstName("Jhon");
        create.setLastName("Dou");
        create.setEmail("mail@mail.ua");
        CustomerReadDTO customerReadDTO = customerService.createCustomer(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(customerReadDTO);
        Assert.assertNotNull(customerReadDTO.getId());

        Customer customer = customerRepository.findById(customerReadDTO.getId()).get();
        Assertions.assertThat(customerReadDTO).isEqualToComparingFieldByField(customer);
    }

    @Test
    public void testPatchCustomer() {
        Customer customer = createCustomer();

        CustomerPatchDTO patch = new CustomerPatchDTO();
        patch.setLogin("test");
        patch.setFirstName("Joe");
        patch.setLastName("Dou");
        patch.setEmail("nomail@i.ua");
        CustomerReadDTO read = customerService.patchCustomer(customer.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        customer = customerRepository.findById(read.getId()).get();
        Assertions.assertThat(customer).isEqualToComparingFieldByField(read);
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

        Customer customerAfterUpdate = customerRepository.findById(read.getId()).get();

        Assert.assertNotNull(customerAfterUpdate.getLogin());
        Assert.assertNotNull(customerAfterUpdate.getFirstName());
        Assert.assertNotNull(customerAfterUpdate.getLastName());
        Assert.assertNotNull(customerAfterUpdate.getEmail());

        Assertions.assertThat(customer).isEqualToComparingFieldByField(customerAfterUpdate);
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