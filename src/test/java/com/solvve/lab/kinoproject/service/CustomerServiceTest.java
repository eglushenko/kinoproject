package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.customer.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPutDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerReadDTO;
import com.solvve.lab.kinoproject.enums.Role;
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
        customer.setRole(Role.USER);
        return customerRepository.save(customer);
    }

    @Test
    public void testGetCustomer() {
        Customer customer = createCustomer();
        CustomerReadDTO customerReadDTO = customerService.getCustomer(customer.getId());
        Assertions.assertThat(customerReadDTO).isEqualToIgnoringGivenFields(customer, "createdAt", "updatedAt");

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
        create.setRole(Role.USER);
        CustomerReadDTO customerReadDTO = customerService.createCustomer(create);
        Assertions.assertThat(create).isEqualToIgnoringGivenFields(customerReadDTO, "createdAt", "updatedAt");
        Assert.assertNotNull(customerReadDTO.getId());

        Customer customer = customerRepository.findById(customerReadDTO.getId()).get();
        Assertions.assertThat(customerReadDTO).isEqualToIgnoringGivenFields(customer, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchCustomer() {
        Customer customer = createCustomer();

        CustomerPatchDTO patch = new CustomerPatchDTO();
        patch.setLogin("test");
        patch.setFirstName("Joe");
        patch.setLastName("Dou");
        patch.setEmail("nomail@i.ua");
        patch.setRole(Role.USER);
        CustomerReadDTO read = customerService.patchCustomer(customer.getId(), patch);

        Assertions.assertThat(patch).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        customer = customerRepository.findById(read.getId()).get();
        Assertions.assertThat(customer).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPutCustomer() {
        Customer customer = createCustomer();

        CustomerPutDTO put = new CustomerPutDTO();
        put.setLogin("test");
        put.setFirstName("Joe");
        put.setLastName("Dou");
        put.setEmail("nomail@i.ua");
        put.setRole(Role.USER);
        CustomerReadDTO read = customerService.updateCustomer(customer.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        customer = customerRepository.findById(read.getId()).get();
        Assertions.assertThat(customer).isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
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

        Assertions.assertThat(customer).isEqualToIgnoringGivenFields(customerAfterUpdate, "createdAt", "updatedAt");
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