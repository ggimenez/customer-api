package com.gabri.coding.customerapi.repository;

import com.gabri.coding.customerapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Song data access object interface
 */
@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {
    public Customer findByName(String name);
}
