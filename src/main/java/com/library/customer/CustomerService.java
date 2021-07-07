package com.library.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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

    List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertCustomerToDTO)
                .collect(Collectors.toList());
    }

    CustomerDTO addCustomer(CustomerDTO customerDTO) {
        customerRepository.save(convertDTOToCustomer(customerDTO));
        return customerDTO;
    }

    CustomerDTO findCustomer(UUID customerId) {
        final Customer customer = customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        return convertCustomerToDTO(customer);

    }

    CustomerDTO updateCustomer(UUID customerId, CustomerDTO newCustomer) {
        final CustomerDTO existingCustomerDTO = findCustomer(customerId);
        final Customer existingCustomer = convertDTOToCustomer(existingCustomerDTO);
        existingCustomer.update(convertDTOToCustomer(newCustomer));
        customerRepository.save(existingCustomer);
        return convertCustomerToDTO(existingCustomer);
    }

    void deleteCustomer(UUID customerId) {
        checkIfCustomerExistById(customerId);
        customerRepository.deleteCustomerByCustomerId(customerId);
    }

    void checkIfCustomerExistById(UUID customerId) {
        final boolean exists = customerRepository.existsByCustomerId(customerId);
        if (!exists) {
            throw new CustomerNotFoundException();
        }
    }

}
