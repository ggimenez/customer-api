package com.gabri.coding.customerapi;

import com.gabri.coding.customerapi.dto.CustomerDTO;
import com.gabri.coding.customerapi.service.CustomersService;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(classes = CustomersApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = {
        "classpath:application-test.properties"
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceTest {

    @Autowired
    private CustomersService customersService;

    @Container
    public static MySQLContainer container = new MySQLContainer("mysql:latest")
            .withDatabaseName("customers_test_db")
            .withUsername("Test")
            .withPassword("Test");

    @Container
    public static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:6.2-alpine")).withExposedPorts(6379);


    @BeforeAll
    public static void setUp(){
        container.withReuse(true);
        container.withInitScript("src/test/resources/db.sql");
        container.start();
        redis.start();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }


    @Test
    @Order(1)
    public void getCustomers(){

        CustomerDTO customerToCreate1 = new CustomerDTO("the Name:1", "the Last Name:1",
                "The Address:1", "The Email:1", "The Phone Number:1");

        CustomerDTO customerToCreate2 = new CustomerDTO("the Name:2", "the Last Name:2",
                "The Address:2", "The Email:2", "The Phone Number:2");


        CustomerDTO customerSaved = customersService.createCustomer(customerToCreate1);
        assertNotNull(customerSaved);

        customerSaved = customersService.createCustomer(customerToCreate2);
        assertNotNull(customerSaved);

        List<CustomerDTO> customersListSaved = customersService.getCustomers();
        assertNotNull(customersListSaved);
        assertEquals( 2, customersListSaved.size());

        assertEquals(1,
                customersListSaved.stream().filter(customerDTO -> customerDTO.getName().equals("the Name:1")).collect(Collectors.toList()).size());
        assertEquals(1,
                customersListSaved.stream().filter(customerDTO -> customerDTO.getName().equals("the Name:2")).collect(Collectors.toList()).size());
    }

    @Test
    @Order(2)
    public void testCreateCustomer(){

        CustomerDTO customerToCreate = new CustomerDTO("the Name:3", "the Last Name:3",
                "The Address:3", "The Email:3", "The Phone Number:3");
        CustomerDTO customerSaved = customersService.createCustomer(customerToCreate);
        assertNotNull(customerSaved);
        assertEquals( "the Name:3", customerSaved.getName());
        assertEquals(customerToCreate.getName(), customerSaved.getName());
    }

    @Test
    @Order(3)
    public void testgetCustomerById(){

        CustomerDTO customerToCreate = new CustomerDTO("the Name:4", "the Last Name:4",
                "The Address:4", "The Email:4", "The Phone Number:4");
        CustomerDTO customerSaved = customersService.createCustomer(customerToCreate);
        assertNotNull(customerSaved);

        Optional<CustomerDTO> customerSearchedOptional = customersService.getCustomerById(customerSaved.getId());

        assertEquals(true, customerSearchedOptional.isPresent());
        assertEquals(customerSaved.getName(), customerSearchedOptional.get().getName());
    }


    @AfterAll
    public static void tearDown(){
        container.stop();
        redis.stop();
    }
}