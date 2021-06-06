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

    public void addCustomer(Customer customer) {
        Optional<Customer> customerByFirstNameAndLastName = customerRepository.findCustomerByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());
        if ( customerByFirstNameAndLastName.isPresent() ) {
            throw new IllegalStateException("Customer already exists");
        }
        customerRepository.save(customer);
    }

    void deleteCustomer(Customer customer) {
        Optional<Customer> customerByFirstNameAndLastName = customerRepository.findCustomerByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());
        if ( customerByFirstNameAndLastName.isEmpty() ) {
            throw new IllegalStateException("Customer does not exist");
        }
        customerRepository.delete(customer);
    }

    List<Customer>findCustomers(){
       return customerRepository.findAll();
    }
}
