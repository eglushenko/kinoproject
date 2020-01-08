package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.CustomerReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerReadDTO getCustomer(UUID uuid) {
        Customer customer = customerRepository.findById(uuid)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(Customer.class, uuid);
                });
        return readDTObyUUID(customer);
    }

    public CustomerReadDTO readDTObyUUID(Customer customer) {
        CustomerReadDTO customerReadDTO = new CustomerReadDTO();
        customerReadDTO.setId(customer.getId());
        customerReadDTO.setUserName(customer.getUserName());
        customerReadDTO.setFirstName(customer.getFirstName());
        customerReadDTO.setLastName(customer.getLastName());
        customerReadDTO.setEmail(customer.getEmail());
        return customerReadDTO;
    }
}
