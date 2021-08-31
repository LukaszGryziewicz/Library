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
    @Autowired
    private CustomerFacade customerFacade;
    ObjectMapper objectMapper = new ObjectMapper();

    private CustomerDTO createCustomer(String firstName, String lastName) {
        CustomerDTO customer = new CustomerDTO(firstName, lastName);
        customerFacade.addCustomer(customer);
        return customer;
    }

    @Test
    void shouldReturnAllCustomers() throws Exception {
        //given
        CustomerDTO customer1 = createCustomer("John", "Smith");
        CustomerDTO customer2 = createCustomer("Richard", "Williams");
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(customer1.getCustomerId()))
                .andExpect(jsonPath("$[0].firstName").value(customer1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(customer1.getLastName()))
                .andExpect(jsonPath("$[1].customerId").value(customer2.getCustomerId()))
                .andExpect(jsonPath("$[1].firstName").value(customer2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(customer2.getLastName()));
    }

    @Test
    void shouldAddCustomer() throws Exception {
        //when
        CustomerDTO customer = new CustomerDTO("John", "Smith");
        String content = objectMapper.writeValueAsString(customer);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
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
        CustomerDTO customer = createCustomer("John", "Smith");
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + customer.getCustomerId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()));
    }

    @Test
    void shouldFindCustomerById() throws Exception {
        //given
        CustomerDTO customer = createCustomer("John", "Smith");
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + customer.getCustomerId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        //given
        CustomerDTO customer1 = createCustomer("John", "Smith");
        CustomerDTO customer2 = new CustomerDTO("Richard", "Williams");
        String content = objectMapper.writeValueAsString(customer2);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/" + customer1.getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customer1.getCustomerId()))
                .andExpect(jsonPath("$.firstName").value(customer2.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer2.getLastName()));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        //given
        CustomerDTO customer = createCustomer("John", "Smith");
        //expect
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + customer.getCustomerId()))
                .andExpect(status().isOk());
    }
}