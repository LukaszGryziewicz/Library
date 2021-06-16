package com.library.customerTests;

import com.library.customer.Customer;
import com.library.customer.CustomerRepository;
import com.library.customer.CustomerService;
import com.library.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @Test
    void shouldAddCustomerToDatabase() {
        //given
        Customer customer1 = new Customer("Adam", "Dominik");
        //when
        customerService.addCustomer(customer1);
        //then
        assertThat(customerRepository.findAll()).contains(customer1);
    }

    @Test
    void shouldFindAllCustomersInDatabase() {
        //given
        Customer customer1 = new Customer("Adam", "Dominik");
        Customer customer2 = new Customer("Łukasz", "Gryziewicz");
        customerRepository.saveAll(Arrays.asList(customer1, customer2));
        //when
        final List<Customer> customers = customerService.getCustomers();
        //then
        assertThat(customers).containsExactlyInAnyOrder(customer1, customer2);
    }

    @Test
    void shouldDeleteCustomerFromDatabase() {
        Customer customer1 = new Customer("Adam", "Dominik");
        customerRepository.save(customer1);
        //when
        customerService.deleteCustomer(customer1.getId());
        //then
        assertThat(customerRepository.findAll()).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingCustomerThatDoesNotExist() {
        Customer customer1 = new Customer("Adam", "Dominik");
        //when
        Throwable thrown = catchThrowable(() -> customerService.deleteCustomer(customer1.getId()));
        //than
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void shouldFindCustomer() {
        //given
        Customer customer1 = new Customer("Adam", "Dominik");
        customerRepository.save(customer1);
        //when
        Customer customer = customerService.findCustomer(customer1.getId());
        //then
        assertThat(customer).isEqualTo(customer1);
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNotFound() {
        //given
        Customer customer1 = new Customer("Adam", "Dominik");
        //when
        Throwable thrown = catchThrowable(() -> customerService.findCustomer(customer1.getId()));
        //then
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void shouldUpdateCustomer() {
        //given
        Customer customer1 = new Customer("Adam", "Dominik");
        Customer customer2 = new Customer("Łukasz", "Gryziewicz");
        customerRepository.save(customer1);
        //when
        customerService.updateCustomer(customer1.getId(), customer2);
        //then
        assertThat(customer1.getFirstName()).isEqualTo(customer2.getFirstName());
        assertThat(customer1.getLastName()).isEqualTo(customer2.getLastName());

    }

}
