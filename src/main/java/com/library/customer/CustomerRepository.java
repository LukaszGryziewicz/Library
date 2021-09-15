package com.library.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);

    @Transactional
    void deleteCustomerByCustomerId(String customerId);
}
