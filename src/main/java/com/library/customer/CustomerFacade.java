package com.library.customer;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomerFacade {

    private final CustomerService customerService;

    public CustomerFacade(CustomerService customerService) {
        this.customerService = customerService;
    }

    CustomerDTO convertCustomerToDTO(Customer customer) {
        return customerService.convertCustomerToDTO(customer);
    }

    Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        return customerService.convertDTOToCustomer(customerDTO);
    }

    List<CustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        return customerService.addCustomer(customerDTO);
    }

    public CustomerDTO findCustomer(UUID customerId) {
        return customerService.findCustomer(customerId);
    }

    CustomerDTO updateCustomer(UUID customerId, CustomerDTO newCustomer) {
        return customerService.updateCustomer(customerId, newCustomer);
    }

    void deleteCustomer(UUID customerId) {
        customerService.deleteCustomer(customerId);
    }

    void checkIfCustomerExistById(UUID customerId) {
        customerService.checkIfCustomerExistById(customerId);
    }
}
