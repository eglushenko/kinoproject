package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
}
