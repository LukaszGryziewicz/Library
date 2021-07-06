package com.library.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldAddCustomerToDatabase() {
        //given
        CustomerDTO customer1 = new CustomerDTO("Adam", "Dominik");
        //when
        customerService.addCustomer(customer1);
        //then
        final CustomerDTO customer = customerService.findCustomer(customer1.getCustomerId());
        assertThat(customer).isNotNull();
    }

    @Test
    void shouldFindAllCustomersInDatabase() {
        //given
        CustomerDTO customer1 = new CustomerDTO("Adam", "Dominik");
        CustomerDTO customer2 = new CustomerDTO("Łukasz", "Gryziewicz");
        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);
        //when
        final List<CustomerDTO> customers = customerService.getCustomers();
        //then
        assertThat(customers.size()).isEqualTo(2);
        assertThat(customers.get(0).getCustomerId()).isEqualTo(customer1.getCustomerId());
        assertThat(customers.get(0).getFirstName()).isEqualTo(customer1.getFirstName());
        assertThat(customers.get(0).getLastName()).isEqualTo(customer1.getLastName());
        assertThat(customers.get(1).getCustomerId()).isEqualTo(customer2.getCustomerId());
        assertThat(customers.get(1).getFirstName()).isEqualTo(customer2.getFirstName());
        assertThat(customers.get(1).getLastName()).isEqualTo(customer2.getLastName());

    }

    @Test
    void shouldDeleteCustomerFromDatabase() {
        CustomerDTO customer1 = new CustomerDTO("Adam", "Dominik");
        customerService.addCustomer(customer1);
        //when
        customerService.deleteCustomer(customer1.getCustomerId());
        //then
        final List<CustomerDTO> customer = customerService.getCustomers();
        assertThat(customer).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingCustomerThatDoesNotExist() {
        CustomerDTO customer1 = new CustomerDTO("Adam", "Dominik");
        //when
        Throwable thrown = catchThrowable(() ->
                customerService.deleteCustomer(customer1.getCustomerId()));
        //than
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void shouldFindCustomer() {
        //given
        CustomerDTO customer1 = new CustomerDTO("Adam", "Dominik");
        customerService.addCustomer(customer1);
        //when
        CustomerDTO customer = customerService.findCustomer(customer1.getCustomerId());
        //then
        assertThat(customer).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNotFound() {
        //given
        CustomerDTO customer1 = new CustomerDTO("Adam", "Dominik");
        //when
        Throwable thrown = catchThrowable(() ->
                customerService.findCustomer(customer1.getCustomerId()));
        //then
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void shouldUpdateCustomer() {
        //given
        CustomerDTO customer1 = new CustomerDTO("Adam", "Dominik");
        CustomerDTO customer2 = new CustomerDTO("Łukasz", "Gryziewicz");
        customerService.addCustomer(customer1);
        //when
        customerService.updateCustomer(customer1.getCustomerId(), customer2);
        //then
        final List<CustomerDTO> customers = customerService.getCustomers();
        assertThat(customers.get(1).getCustomerId()).isEqualTo(customer1.getCustomerId());
        assertThat(customers.get(1).getFirstName()).isEqualTo(customer2.getFirstName());
        assertThat(customers.get(1).getLastName()).isEqualTo(customer2.getLastName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingCustomerThatDoesNotExist() {
        //given
        CustomerDTO customer1 = new CustomerDTO("Adam", "Dominik");
        CustomerDTO customer2 = new CustomerDTO("Łukasz", "Gryziewicz");
        //when
        Throwable thrown = catchThrowable(() ->
                customerService.updateCustomer(customer1.getCustomerId(), customer2));
        //then
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class);
    }
}
