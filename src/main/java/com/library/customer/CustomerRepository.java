package com.library.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);

    void deleteCustomerByCustomerId(String customerId);

    List<Customer> findCustomersByCustomerIdIn(List<String> listOfCustomerId);
}
