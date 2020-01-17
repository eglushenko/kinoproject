package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.dto.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.CustomerReadDTO;
import com.solvve.lab.kinoproject.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Search a Customer with an ID", response = Customer.class)
    public CustomerReadDTO getCustomer(@PathVariable UUID id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a customer")
    public CustomerReadDTO createCustomer(@RequestBody CustomerCreateDTO createDTO) {
        return customerService.createCustomer(createDTO);
    }

    @PatchMapping("/{id}")
    public CustomerReadDTO patchCustomer(@PathVariable UUID id, @RequestBody CustomerPatchDTO patch) {
        return customerService.patchCustomer(id, patch);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
    }

}
