package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.CustomerCreateDTO;
import com.solvve.lab.kinoproject.dto.CustomerPatchDTO;
import com.solvve.lab.kinoproject.dto.CustomerReadDTO;
import com.solvve.lab.kinoproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerReadDTO getCustomer(@PathVariable UUID id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    public CustomerReadDTO createCustomer(@RequestBody CustomerCreateDTO createDTO) {
        return customerService.createCustomer(createDTO);
    }

    @PatchMapping("/{id}")
    public CustomerReadDTO patchCustomer(@PathVariable UUID id, @RequestBody CustomerPatchDTO patch) {
        return customerService.patchCustomer(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
    }

}
