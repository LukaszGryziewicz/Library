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

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        final Rental rental = rentalService.rent(
                customer.getId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(get("/rental"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer").value(rental.getCustomer()))
                .andExpect(jsonPath("$[0].book").value(rental.getBook()));
    }

    @Test
    void shouldFindRentalsOfCustomer() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        Customer customer2 = new Customer("Łukasz", "Gryziewicz");
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        customerRepository.saveAll(asList(customer, customer2));
        bookRepository.saveAll(asList(book, book2));
        rentalService.rent(
                customer.getId(), book2.getTitle(),
                book2.getAuthor(), LocalDateTime.now()
        );
        rentalService.rent(
                customer2.getId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(get("/rental/customerRentals/" + customer.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer").value(customer))
                .andExpect(jsonPath("$[0].book").value(book2));
    }

    @Test
    void shouldFindRentalsOfBook() throws Exception {
        //given
        Customer customer = new Customer("Adam", "Dominik");
        Customer customer2 = new Customer("Łukasz", "Gryziewicz");
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerRepository.saveAll(asList(customer, customer2));
        bookRepository.save(book);
        rentalService.rent(
                customer.getId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        book.setRented(false);
        rentalService.rent(
                customer2.getId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(get("/rental/bookRentals/" + book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer").value(customer))
                .andExpect(jsonPath("$[0].book").value(book))
                .andExpect(jsonPath("$[1].customer").value(customer2))
                .andExpect(jsonPath("$[1].book").value(book));
    }

    @Test
    void shouldAddRental() throws Exception {
        //when
        Customer customer = new Customer("Adam", "Dominik");
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerRepository.save(customer);
        bookRepository.save(book);
        //expect
        mockMvc.perform(post(("/rental/" + customer.getId() + "/" + book.getTitle()) + "/" + book.getAuthor()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer").value(customer))
                .andExpect(jsonPath("$.book").value(book));
    }

    @Test
    void shouldFindRentalById() throws Exception {
        //when
        Customer customer = new Customer("Adam", "Dominik");
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerRepository.save(customer);
        bookRepository.save(book);
        final Rental rental = rentalService.rent(
                customer.getId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(get("/rental/" + rental.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer").value(customer))
                .andExpect(jsonPath("$.book").value(book));
    }

    @Test
    void shouldEndRental() throws Exception {
        //when
        Customer customer = new Customer("Adam", "Dominik");
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerRepository.save(customer);
        bookRepository.save(book);
        Rental rental = rentalService.rent(
                customer.getId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(put("/rental/endRental/" + rental.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteRental() throws Exception {
        //when
        Customer customer = new Customer("Adam", "Dominik");
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerRepository.save(customer);
        bookRepository.save(book);
        Rental rental = rentalService.rent(
                customer.getId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(delete("/rental/delete/" + rental.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
