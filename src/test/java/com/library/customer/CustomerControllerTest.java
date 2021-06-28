package com.library.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllCustomers() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        Customer customer2 = new Customer("Łukasz", "Gryziewicz");
        //when
        when(customerService.getCustomers()).thenReturn(List.of(customer, customer2));
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/customer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Adam"))
                .andExpect(jsonPath("$[0].lastName").value("Dominik"))
                .andExpect(jsonPath("$[1].firstName").value("Łukasz"))
                .andExpect(jsonPath("$[1].lastName").value("Gryziewicz"));
    }

    @Test
    void shouldReturnCustomerWithGivenId() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        customer.setId(1);
        //when
        when(customerService.findCustomer(customer.getId())).thenReturn(customer);
        //than
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Dominik"));

    }

}

