package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.TestJPAQueryFactoryConfiguration;
import com.genesisairport.reservation.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(TestJPAQueryFactoryConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findByEmail_WhenCustomerExists_ReturnCustomer() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        customer.setName("John Doe");
        customerRepository.save(customer);

        Optional<Customer> foundCustomerOptional = customerRepository.findByEmail("test@example.com");

        assertTrue(foundCustomerOptional.isPresent());
        Customer foundCustomer = foundCustomerOptional.get();
        assertEquals("test@example.com", foundCustomer.getEmail());
    }

    @Test
    void findByEmail_WhenCustomerDoesNotExist_ReturnEmptyOptional() {
        Optional<Customer> foundCustomerOptional = customerRepository.findByEmail("nonexistent@example.com");

        assertTrue(foundCustomerOptional.isEmpty());
    }
}
