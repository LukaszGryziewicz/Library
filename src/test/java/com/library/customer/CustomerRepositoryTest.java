package com.library.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFindCustomerByCustomerId() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.setCustomerId("ad8b7f39-dbc6-4c56-8289-5dcea52d0681");
        customerRepository.save(customer);
        //when
        Optional<Customer> customerByCustomerId = customerRepository
                .findCustomerByCustomerId(customer.getCustomerId());
        //then
        assertThat(customerByCustomerId).isPresent();
    }

    @Test
    void shouldNotFindCustomerByCustomerId() {
        //given
        String id = "ad8b7f39-dbc6-4c56-8289-5dcea52d0681";
        //when
        Optional<Customer> customerByCustomerId = customerRepository
                .findCustomerByCustomerId(id);
        //then
        assertThat(customerByCustomerId).isNotPresent();
    }

    @Test
    void shouldReturnTrueWhenCustomerExists() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.setCustomerId("ad8b7f39-dbc6-4c56-8289-5dcea52d0681");
        customerRepository.save(customer);
        //when
        boolean existsByCustomerId = customerRepository
                .existsByCustomerId(customer.getCustomerId());
        //then
        assertThat(existsByCustomerId).isTrue();
    }

    @Test
    void shouldReturnFalseWhenCustomerDoesNotExist() {
        //given
        String id = "ad8b7f39-dbc6-4c56-8289-5dcea52d0681";
        //when
        boolean existsByCustomerId = customerRepository
                .existsByCustomerId(id);
        //then
        assertThat(existsByCustomerId).isFalse();
    }

    @Test
    void shouldDeleteCustomerByCustomerId() {
        //given
        Customer customer = new Customer("John", "Smith");
        customer.setCustomerId("ad8b7f39-dbc6-4c56-8289-5dcea52d0681");
        customerRepository.save(customer);
        //when
        customerRepository.deleteCustomerByCustomerId(customer.getCustomerId());
        //then
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).isEmpty();
    }
}