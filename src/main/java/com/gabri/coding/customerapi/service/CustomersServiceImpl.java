package com.gabri.coding.customerapi.service;

import com.gabri.coding.customerapi.dto.CustomerDTO;
import com.gabri.coding.customerapi.exceptions.ResourceNotFoundException;
import com.gabri.coding.customerapi.exceptions.ServiceLayerException;
import com.gabri.coding.customerapi.model.Customer;
import com.gabri.coding.customerapi.repository.CustomersRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Customers Service implementation
 */
@Service
public class CustomersServiceImpl implements CustomersService {

    CustomersRepository customersRepository;
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(CustomersServiceImpl.class);

    @Autowired
    public CustomersServiceImpl(CustomersRepository customersRepository, ModelMapper modelMapper){
        this.customersRepository = customersRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CustomerDTO> getCustomers() {

        try {
            List<CustomerDTO> customersDTO = new ArrayList<>();
            List<Customer> customersFromDB = customersRepository.findAll();
            for(Customer oneCustomerFromDB : customersFromDB){
                customersDTO.add(modelMapper.map(oneCustomerFromDB, CustomerDTO.class));
            }

            return  customersDTO;
        }catch (Exception e){
            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(int id) {
        try {
            Optional<Customer> customerOptional = customersRepository.findById(id);
            if(customerOptional.isPresent()){
                return Optional.of(modelMapper.map(customerOptional.get(), CustomerDTO.class));
            }else{
                throw new ResourceNotFoundException("Customer", (long) id);
            }
        }catch (ResourceNotFoundException resourceNotFoundException){
            throw resourceNotFoundException;
        }
        catch (Exception e){
            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        try{
            Customer customerEntity = modelMapper.map(customerDTO, Customer.class);
            Customer customerSaved = customersRepository.save(customerEntity);
            return modelMapper.map(customerSaved, CustomerDTO.class);
        }catch (Exception e){
            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, int id) {
        try {
            customerDTO.setId(id);
            Customer customerEntity = modelMapper.map(customerDTO, Customer.class);
            Customer customerUpdated = customersRepository.save(customerEntity);
            return modelMapper.map(customerUpdated, CustomerDTO.class);

        }catch (Exception e){
            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }
    }

    @Override
    public void deleteCustomer(int id) {
        try {
            if(!customersRepository.findById(id).isPresent()){
                throw new ResourceNotFoundException("Customer", (long) id);
            };
            customersRepository.deleteById(id);
        }catch (ResourceNotFoundException resourceNotFoundException){
            throw resourceNotFoundException;
        }
        catch (Exception e){

            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }
    }

    @Override
    public List<CustomerDTO> findCustomerByName(String name) {

        try {
            List<CustomerDTO> customersFilteredDTO = new ArrayList<>();
            List<Customer> allCustomersFromDBMatched = customersRepository.findAll().stream().filter(
                    customer -> customer.getName().contains(name)).collect(Collectors.toList());

            allCustomersFromDBMatched.forEach(oneCustomer -> customersFilteredDTO.add(modelMapper.map(oneCustomer, CustomerDTO.class)));

            return customersFilteredDTO;
        }catch (Exception e){
            logger.error("An unexpected error has occurred: " + e.getMessage());
            throw new ServiceLayerException("An unexpected error has occurred");
        }
    }
}
