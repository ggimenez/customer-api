package com.gabri.coding.customerapi.service;

import com.gabri.coding.customerapi.dto.CustomerDTO;
import com.gabri.coding.customerapi.model.Customer;
import com.gabri.coding.customerapi.repository.CustomersRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    public CustomersServiceImpl(CustomersRepository customersRepository, ModelMapper modelMapper){
        this.customersRepository = customersRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CustomerDTO> getCustomers() {

        List<CustomerDTO> customersDTO = new ArrayList<>();
        List<Customer> customersFromDB = customersRepository.findAll();
        for(Customer oneCustomerFromDB : customersFromDB){
            customersDTO.add(modelMapper.map(oneCustomerFromDB, CustomerDTO.class));
        }

        return  customersDTO;
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(int id) {
        Optional<Customer> customerOptional = customersRepository.findById(id);
        if(customerOptional.isPresent()){
            return Optional.of(modelMapper.map(customerOptional.get(), CustomerDTO.class));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        try{
            Customer customerEntity = modelMapper.map(customerDTO, Customer.class);
            Customer customerSaved = customersRepository.save(customerEntity);
            return modelMapper.map(customerSaved, CustomerDTO.class);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, int id) {
        customerDTO.setId(id);
        Customer customerEntity = modelMapper.map(customerDTO, Customer.class);
        Customer customerUpdated = customersRepository.save(customerEntity);
        return modelMapper.map(customerUpdated, CustomerDTO.class);
    }

    @Override
    public void deleteCustomer(int id) {
        customersRepository.deleteById(id);
    }

    @Override
    public List<CustomerDTO> findCustomerByName(String name) {

        List<CustomerDTO> customersFilteredDTO = new ArrayList<>();
        List<Customer> allCustomersFromDBMatched = customersRepository.findAll().stream().filter(
                customer -> customer.getName().contains(name)).collect(Collectors.toList());

        allCustomersFromDBMatched.forEach(oneCustomer -> customersFilteredDTO.add(modelMapper.map(oneCustomer, CustomerDTO.class)));

        return customersFilteredDTO;
    }
}
