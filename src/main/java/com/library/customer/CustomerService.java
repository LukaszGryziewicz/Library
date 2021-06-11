package com.library.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Customer> customerByFirstNameAndLastName = customerRepository.findCustomerByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());

        if ( customerByFirstNameAndLastName.isPresent() ) {
            throw new IllegalStateException("Customer already exists");
        }
        customerRepository.save(customer);
        return customer;
    }

    public Customer findCustomer(Long id) {
        return customerRepository.findCustomerById(id)
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> customerById = customerRepository.findCustomerById(id);
        customerById.orElseThrow(() -> new IllegalStateException("Customer not found"));

        Customer existingCustomer = customerById.get();
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());

        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.findCustomerById(id)
                .orElseThrow(() -> new IllegalStateException("Customer not found"));

        customerRepository.deleteById(id);
    }
}
