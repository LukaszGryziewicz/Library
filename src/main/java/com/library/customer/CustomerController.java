package com.library.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping("/customers")
class CustomerController {

    private final CustomerFacade customerFacade;

    CustomerController(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    @GetMapping()
    ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        final List<CustomerDTO> customers = customerFacade.getCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        final CustomerDTO newCustomer = customerFacade.addCustomer(customerDTO);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    ResponseEntity<CustomerDTO> findCustomer(@PathVariable("id") String customerId) {
        CustomerDTO customer = customerFacade.findCustomer(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") String customerId, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerFacade.updateCustomer(customerId, customerDTO);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable("id") String customerId) {
        customerFacade.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
