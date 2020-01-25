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

    private Customer getCustomerRequired(UUID id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Customer.class, id));
    }

    public CustomerReadDTO getCustomer(UUID id) {
        Customer customer = getCustomerRequired(id);
        return toReadDTO(customer);
    }

    public CustomerReadDTO toReadDTO(Customer customer) {
        CustomerReadDTO customerReadDTO = new CustomerReadDTO();
        customerReadDTO.setId(customer.getId());
        customerReadDTO.setLogin(customer.getLogin());
        customerReadDTO.setFirstName(customer.getFirstName());
        customerReadDTO.setLastName(customer.getLastName());
        customerReadDTO.setEmail(customer.getEmail());
        customerReadDTO.setRole(customer.getRole());
        return customerReadDTO;
    }

    public CustomerReadDTO createCustomer(CustomerCreateDTO create) {
        Customer customer = new Customer();
        customer.setLogin(create.getLogin());
        customer.setFirstName(create.getFirstName());
        customer.setLastName(create.getLastName());
        customer.setEmail(create.getEmail());
        customer.setRole(create.getRole());
        customer = customerRepository.save(customer);
        return toReadDTO(customer);
    }

    public CustomerReadDTO patchCustomer(UUID id, CustomerPatchDTO patch) {
        Customer customer = getCustomerRequired(id);
        if (patch.getFirstName() != null) {
            customer.setFirstName(patch.getFirstName());
        }
        if (patch.getLastName() != null) {
            customer.setLastName(patch.getLastName());
        }
        if (patch.getLogin() != null) {
            customer.setLogin(patch.getLogin());
        }
        if (patch.getEmail() != null) {
            customer.setEmail(patch.getEmail());
        }
        if (patch.getRole() != null) {
            customer.setRole(patch.getRole());
        }
        customer = customerRepository.save(customer);
        return toReadDTO(customer);
    }

    public CustomerReadDTO putCustomer(UUID id, CustomerPutDTO put) {
        Customer customer = getCustomerRequired(id);
        customer.setLogin(put.getLogin());
        customer.setFirstName(put.getFirstName());
        customer.setLastName(put.getLastName());
        customer.setEmail(put.getEmail());
        customer.setRole(put.getRole());
        customer = customerRepository.save(customer);
        return toReadDTO(customer);
    }

    public void deleteCustomer(UUID id) {
        customerRepository.delete(getCustomerRequired(id));
    }
}
