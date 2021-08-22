package com.library.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class CustomerService {

    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    CustomerDTO convertCustomerToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        return customerDTO;
    }

    Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        return customer;
    }

    List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertCustomerToDTO)
                .collect(Collectors.toList());
    }

    CustomerDTO addCustomer(CustomerDTO customerDTO) {
        customerDTO.setCustomerId(UUID.randomUUID().toString());
        customerRepository.save(convertDTOToCustomer(customerDTO));
        return customerDTO;
    }

    CustomerDTO findCustomer(String customerId) {
        final Customer customer = customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        return convertCustomerToDTO(customer);

    }

    private Customer findCustomerEntity(String customerId) {
        return customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    CustomerDTO updateCustomer(String customerId, CustomerDTO updatedCustomer) {
        final Customer existingCustomer = findCustomerEntity(customerId);
        existingCustomer.update(convertDTOToCustomer(updatedCustomer));
        customerRepository.save(existingCustomer);
        return updatedCustomer;
    }

    void deleteCustomer(String customerId) {
        checkIfCustomerExistById(customerId);
        customerRepository.deleteCustomerByCustomerId(customerId);
    }

    void checkIfCustomerExistById(String customerId) {
        final boolean exists = customerRepository.existsByCustomerId(customerId);
        if ( !exists ) {
            throw new CustomerNotFoundException();
        }
    }

}
