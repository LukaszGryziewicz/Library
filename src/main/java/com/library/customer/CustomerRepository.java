package com.library.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByCustomerId(UUID customerId);

    boolean existsByCustomerId(UUID customerId);

    void deleteCustomerByCustomerId(UUID customerId);
}
