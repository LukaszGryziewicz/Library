package com.library.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class CustomerServiceTest {

    @Autowired
    private CustomerFacade customerFacade;

    private CustomerDTO createCustomer(String firstName, String lastName) {
        CustomerDTO customer = new CustomerDTO(firstName, lastName);
        customerFacade.addCustomer(customer);
        return customer;
    }

    @Test
    void shouldAddCustomer() {
        //when
        CustomerDTO customer = createCustomer("John", "Smith");
        //then
        final CustomerDTO customerFromDB = customerFacade.findCustomer(customer.getCustomerId());
        assertThat(customerFromDB).isEqualTo(customer);
    }

    @Test
    void shouldFindAllCustomers() {
        //given
        CustomerDTO customer1 = createCustomer("John", "Smith");
        CustomerDTO customer2 = createCustomer("Richard", "Williams");
        //when
        final List<CustomerDTO> customerList = customerFacade.getCustomers();
        //then
        assertThat(customerList).containsExactlyInAnyOrder(customer1, customer2);

    }

    @Test
    void shouldDeleteCustomer() {
        //given
        CustomerDTO customer = createCustomer("John", "Smith");
        //when
        customerFacade.deleteCustomer(customer.getCustomerId());
        //then
        final List<CustomerDTO> customerList = customerFacade.getCustomers();
        assertThat(customerList).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingCustomerThatDoesNotExist() {
        //given
        String randomId = randomUUID().toString();
        //when
        Throwable thrown = catchThrowable(() ->
                customerFacade.deleteCustomer(randomId));
        //then
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    void shouldFindCustomerById() {
        //given
        CustomerDTO customer = createCustomer("John", "Smith");
        //when
        CustomerDTO customerFromDB = customerFacade.findCustomer(customer.getCustomerId());
        //then
        assertThat(customer).isEqualTo(customerFromDB);
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNotFound() {
        //given
        String randomId = randomUUID().toString();
        //when
        Throwable thrown = catchThrowable(() ->
                customerFacade.findCustomer(randomId));
        //then
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void shouldUpdateCustomer() {
        //given
        CustomerDTO customer1 = createCustomer("John", "Smith");
        CustomerDTO customer2 = new CustomerDTO("Richard", "Williams");
        //when
        customerFacade.updateCustomer(customer1.getCustomerId(), customer2);
        //then
        final List<CustomerDTO> customers = customerFacade.getCustomers();
        CustomerDTO customerFromDB = customers.get(0);
        assertThat(customerFromDB.getCustomerId()).isEqualTo(customer1.getCustomerId());
        assertThat(customerFromDB.getFirstName()).isEqualTo(customer2.getFirstName());
        assertThat(customerFromDB.getLastName()).isEqualTo(customer2.getLastName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingCustomerThatDoesNotExist() {
        //given
        String randomId = randomUUID().toString();
        CustomerDTO customer = new CustomerDTO("Richard", "Williams");
        //when
        Throwable thrown = catchThrowable(() ->
                customerFacade.updateCustomer(randomId, customer));
        //then
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class);
    }
}
