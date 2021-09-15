package com.library.customer;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

@Service
class CustomerService {

    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertCustomerToDTO)
                .collect(toList());
    }

    CustomerDTO addCustomer(CustomerDTO customerDTO) {
        customerDTO.setCustomerId(randomUUID().toString());
        customerRepository.save(convertDTOToCustomer(customerDTO));
        return customerDTO;
    }

    CustomerDTO findCustomer(String customerId) {
        final Customer customer = customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        return convertCustomerToDTO(customer);

    }

    CustomerDTO updateCustomer(String customerId, CustomerDTO updatedCustomer) {
        final Customer existingCustomer = findCustomerEntity(customerId);
        existingCustomer.update(convertDTOToCustomer(updatedCustomer));
        customerRepository.save(existingCustomer);
        return convertCustomerToDTO(existingCustomer);
    }

    void deleteCustomer(String customerId) {
        checkIfCustomerExistById(customerId);
        customerRepository.deleteCustomerByCustomerId(customerId);
    }

    void checkIfCustomerExistById(String customerId) {
        final boolean exists = customerRepository.existsByCustomerId(customerId);
        if (!exists) {
            throw new CustomerNotFoundException();
        }
    }

    private Customer findCustomerEntity(String customerId) {
        return customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    private CustomerDTO convertCustomerToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        return customerDTO;
    }

    private Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        return customer;
    }
}
