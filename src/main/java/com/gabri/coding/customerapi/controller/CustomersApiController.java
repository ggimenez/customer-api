package com.gabri.coding.customerapi.controller;

import com.gabri.coding.customerapi.dto.CustomerDTO;
import com.gabri.coding.customerapi.service.CustomersService;
import com.gabri.coding.customerapi.service.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Customers Rest Service
 */
@RequestMapping("/api/customer")
@RestController
public class CustomersApiController {

    CustomersService customersService;
    Logger logger = LoggerFactory.getLogger(CustomersApiController.class);

    @Autowired
    public CustomersApiController(CustomersService customersService){
        this.customersService = customersService;
    }

    /**
     * Gets all Customers that were saved
     * @return a Customers List
     */
    @GetMapping("/")
    public ResponseEntity<Object> getAll(){

        return new ResponseEntity<>(customersService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/find-by-name")
    public ResponseEntity<Object> findByName(@RequestParam String name){
        return new ResponseEntity<>(customersService.findCustomerByName(name), HttpStatus.OK);
    }

    /**
     * Gets a Customer by its ID
     * @param id The Customer ID
     * @return Customer
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        Optional<CustomerDTO> songOptional = customersService.getCustomerById(id);
        return new ResponseEntity<>(songOptional.get(), HttpStatus.OK);
    }

    /**
     * Creates and save a new Customer
     * @param customerDTO The CustomerDTO to be created
     */
    @PostMapping("/")
    public ResponseEntity<Object> save(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerSaved = customersService.createCustomer(customerDTO);
        return new ResponseEntity<>(customerSaved, HttpStatus.OK);
    }

    /**
     * Updates a existing Customer
     * @param customerDTO The new song data dto
     * @param id The song ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody CustomerDTO customerDTO, @PathVariable int id) {
        CustomerDTO customerUpdated = customersService.updateCustomer(customerDTO, id);
        return new ResponseEntity<>(customerUpdated, HttpStatus.OK);
    }

    /**
     * Deletes a Customer
     * @param id The Customer ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        customersService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
