package com.library.customer;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerFacade {

    private final CustomerService customerService;

    public CustomerFacade(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        return customerService.addCustomer(customerDTO);
    }

    public CustomerDTO findCustomer(String customerId) {
        return customerService.findCustomer(customerId);
    }

    public void checkIfCustomerExistById(String customerId) {
        customerService.checkIfCustomerExistById(customerId);
    }

    List<CustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }

    CustomerDTO updateCustomer(String customerId, CustomerDTO newCustomer) {
        return customerService.updateCustomer(customerId, newCustomer);
    }

    void deleteCustomer(String customerId) {
        customerService.deleteCustomer(customerId);
    }
}
