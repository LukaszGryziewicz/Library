package com.library.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private CustomerService customerService;

    @Test
    void shouldReturnAllCustomers() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        Customer customer2 = new Customer("Łukasz", "Gryziewicz");
        customerService.addCustomer(customer);
        customerService.addCustomer(customer2);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/customer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$[1].firstName").value(customer2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(customer2.getLastName()));
    }

    @Test
    void shouldAddCustomer() throws Exception {
        //when
        Customer customer = new Customer("Adam", "Dominik");
        String content = objectMapper.writeValueAsString(customer);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()));
    }


    @Test
    void shouldReturnCustomerWithGivenId() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        customerService.addCustomer(customer);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/" + customer.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()));
    }

    @Test
    void shouldFindCustomerById() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        customerService.addCustomer(customer);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/" + customer.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        Customer customer2 = new Customer("Łukasz", "Gryziewicz");
        customerService.addCustomer(customer);
        String content = objectMapper.writeValueAsString(customer2);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update/" + customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(customer2.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer2.getLastName()));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        customerService.addCustomer(customer);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/delete/" + customer.getId()))
                .andExpect(status().isOk());

    }
}
