package com.library.customerTests;

import com.library.customer.Customer;
import com.library.customer.CustomerRepository;
import com.library.customer.CustomerService;
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
        //than
        assertThat(customerRepository.findAll()).contains(customer1);
    }

    @Test
    void shouldThrowExceptionWhenAddingCustomerThatAlreadyExists() {
        Customer customer1 = new Customer("Adam", "Dominik");
        customerRepository.save(customer1);
        //when
        Throwable thrown = catchThrowable(() ->customerService.addCustomer(customer1));

        //then
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Customer already exists");
    }

    @Test
    void shouldFindAllCustomersInDatabase() {
        //given
        Customer customer1 = new Customer("Adam", "Dominik");
        Customer customer2 = new Customer("Łukasz", "Gryziewicz");
        customerRepository.saveAll(Arrays.asList(customer1, customer2));
        //when
        final List<Customer> customers = customerService.getCustomers();
        //than
        assertThat(customers).containsExactlyInAnyOrder(customer1, customer2);
    }

    @Test
    void shouldDeleteCustomerFromDatabase() {
        Customer customer1 = new Customer("Adam", "Dominik");
        customerRepository.save(customer1);
        //when
        customerService.deleteCustomer(customer1);
        //than
        assertThat(customerRepository.findAll()).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingCustomerThatDoesNotExist() {
        Customer customer1 = new Customer("Adam", "Dominik");
        //when
        Throwable thrown = catchThrowable(() -> customerService.deleteCustomer(customer1));
        //than
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Customer does not exist");
    }

}
