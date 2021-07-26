package com.library.customer;

import org.springframework.stereotype.Component;

import java.util.List;

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

    public CustomerDTO findCustomer(String customerId) {
        return customerService.findCustomer(customerId);
    }

    CustomerDTO updateCustomer(String customerId, CustomerDTO newCustomer) {
        return customerService.updateCustomer(customerId, newCustomer);
    }

    void deleteCustomer(String customerId) {
        customerService.deleteCustomer(customerId);
    }

    void checkIfCustomerExistById(String customerId) {
        customerService.checkIfCustomerExistById(customerId);
    }
}
