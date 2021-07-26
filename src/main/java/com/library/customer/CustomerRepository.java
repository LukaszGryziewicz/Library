package com.library.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);

    void deleteCustomerByCustomerId(String customerId);
}
