package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.customer.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerPutDTO;
import com.solvve.lab.kinoproject.dto.customer.CustomerReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TranslationService translationService;

    private Customer getCustomerRequired(UUID id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Customer.class, id));
    }

    public CustomerReadDTO getCustomer(UUID id) {
        Customer customer = getCustomerRequired(id);
        return translationService.toReadCustomer(customer);
    }

    public CustomerReadDTO createCustomer(CustomerCreateDTO create) {
        Customer customer = translationService.toEntityCustomer(create);
        customer = customerRepository.save(customer);
        return translationService.toReadCustomer(customer);
    }

    public CustomerReadDTO patchCustomer(UUID id, CustomerPatchDTO patch) {
        Customer customer = getCustomerRequired(id);
        translationService.patchEntityCustomer(patch, customer);
        customer = customerRepository.save(customer);
        return translationService.toReadCustomer(customer);
    }

    public CustomerReadDTO putCustomer(UUID id, CustomerPutDTO put) {
        Customer customer = getCustomerRequired(id);
        translationService.putEntityCustomer(put, customer);
        customer = customerRepository.save(customer);
        return translationService.toReadCustomer(customer);
    }

    public void deleteCustomer(UUID id) {
        customerRepository.delete(getCustomerRequired(id));
    }
}
