package com.library.customerTests;

import com.library.customer.Customer;
import com.library.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFindCustomerByFirstAndLastName() {
        //given
        Customer customer1 = new Customer("Adam", "Dominik");
        customerRepository.save(customer1);
        //when
        final Optional<Customer> customerByFirstNameAndLastName = customerRepository.findCustomerByFirstNameAndLastName("Adam", "Dominik");

        //than
        assertThat(customerByFirstNameAndLastName.isPresent()).isTrue();
    }
}
