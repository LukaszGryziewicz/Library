package com.library.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer addCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    public Customer findCustomer(UUID customerId) {
        return customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer updateCustomer(UUID customerId, Customer customer) {
        final Customer existingCustomer = findCustomer(customerId);
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(UUID customerId) {
        checkIfCustomerExistById(customerId);
        customerRepository.deleteCustomerByCustomerId(customerId);
    }

    public void checkIfCustomerExistById(UUID customerId) {
        final boolean exists = customerRepository.existsByCustomerId(customerId);
        if (!exists) {
            throw new CustomerNotFoundException();
        }
    }
}
