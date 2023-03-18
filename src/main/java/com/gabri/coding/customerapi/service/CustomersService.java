package com.gabri.coding.customerapi.service;

import com.gabri.coding.customerapi.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Customer business layer interface
 */
public interface CustomersService {
    /**
     * Gets all Customers
     * @return Customer list
     */
    public abstract List<CustomerDTO> getCustomers();

    /**
     * Gets a Customer by its ID
     * @param id Customer ID
     * @return the Customer
     */
    public abstract Optional<CustomerDTO> getCustomerById(int id);

    /**
     * Creates a new Customer
     * @param customerDTO the new Customer to be created
     */
    public abstract CustomerDTO createCustomer(CustomerDTO customerDTO);

    /**
     * Update a existing Customer
     * @param customerDTO Customer to be updated
     * @param id Customer to be updated ID
     */
    CustomerDTO updateCustomer(CustomerDTO customerDTO, int id);

    /**
     * Deletes a existing Customer
     * @param id Customer to be deleted ID
     */
    void deleteCustomer(int id);

    /**
     * @param name name to find
     * @return List<CustomerDTO> a list of matched Customers
     */
    List<CustomerDTO> findCustomerByName(String name);

}
