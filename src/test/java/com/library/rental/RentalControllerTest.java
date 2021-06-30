package com.library.rental;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.customer.Customer;
import com.library.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class RentalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RentalService rentalService;

    @Test
    void shouldReturnAllRentals() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerRepository.save(customer);
        bookRepository.save(book);
        final Rental rental = rentalService.createRental(customer.getId(), book.getTitle(), book.getAuthor(), LocalDateTime.now());
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/rental"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer").value(rental.getCustomer()))
                .andExpect(jsonPath("$[0].book").value(rental.getBook()));
    }

    @Test
    void shouldAddRental() throws Exception {
        //when
        Customer customer = new Customer("Adam", "Dominik");
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerRepository.save(customer);
        bookRepository.save(book);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.post(("/rental/" + customer.getId() + "/" + book.getTitle()) + "/" + book.getAuthor()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer").value(customer))
                .andExpect(jsonPath("$.book").value(book));
    }
}
