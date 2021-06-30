package com.library.customer;

import com.library.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Customer findCustomer(Long id) {
        return customerRepository.findCustomerById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        final Customer existingCustomer = findCustomer(id);
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        findCustomer(id);
        customerRepository.deleteById(id);
    }
}
