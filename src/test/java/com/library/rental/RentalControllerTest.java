package com.library.rental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

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
    CustomerFacade customerFacade;

    @Autowired
    BookFacade bookFacade;

    @Autowired
    RentalFacade rentalFacade;

    @Test
    void shouldReturnAllRentals() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO("Adam", "Dominik");
        BookDTO book = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerFacade.addCustomer(customer);
        bookFacade.addNewBook(book);
        final RentalDTO rental = rentalFacade.rent(
                customer.getCustomerId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(get("/rental"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(rental.getCustomerId().toString()))
                .andExpect(jsonPath("$[0].bookId").value(rental.getBookId().toString()));
    }

    @Test
    void shouldFindRentalsOfCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO("Adam", "Dominik");
        CustomerDTO customer2 = new CustomerDTO("Łukasz", "Gryziewicz");
        BookDTO book = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        BookDTO book2 = new BookDTO("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        customerFacade.addCustomer(customer);
        customerFacade.addCustomer(customer2);
        bookFacade.addNewBook(book);
        bookFacade.addNewBook(book2);
        rentalFacade.rent(
                customer.getCustomerId(), book2.getTitle(),
                book2.getAuthor(), LocalDateTime.now()
        );
        rentalFacade.rent(
                customer2.getCustomerId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(get("/rental/customerRentals/" + customer.getCustomerId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$[0].bookId").value(book2.getBookId().toString()));
    }

    @Test
    void shouldFindRentalsOfBook() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO("Adam", "Dominik");
        CustomerDTO customer2 = new CustomerDTO("Łukasz", "Gryziewicz");
        BookDTO book = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerFacade.addCustomer(customer);
        customerFacade.addCustomer(customer2);
        bookFacade.addNewBook(book);
        final RentalDTO rental1 = rentalFacade.rent(
                customer.getCustomerId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        rentalFacade.returnBook(rental1.getRentalId(), LocalDateTime.now());
        rentalFacade.rent(
                customer2.getCustomerId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(get("/rental/bookRentals/" + book.getBookId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(customer2.getCustomerId().toString()))
                .andExpect(jsonPath("$[0].bookId").value(book.getBookId().toString()));
    }

    @Test
    void shouldAddRental() throws Exception {
        //when
        CustomerDTO customer = new CustomerDTO("Adam", "Dominik");
        BookDTO book = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerFacade.addCustomer(customer);
        bookFacade.addNewBook(book);
        //expect
        mockMvc.perform(post(("/rental/" + customer.getCustomerId() + "/" + book.getTitle()) + "/" + book.getAuthor()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$.bookId").value(book.getBookId().toString()));
    }

    @Test
    void shouldFindRentalById() throws Exception {
        //when
        CustomerDTO customer = new CustomerDTO("Adam", "Dominik");
        BookDTO book = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerFacade.addCustomer(customer);
        bookFacade.addNewBook(book);
        final RentalDTO rental = rentalFacade.rent(
                customer.getCustomerId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(get("/rental/" + rental.getRentalId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$.bookId").value(book.getBookId().toString()));
    }

    @Test
    void shouldEndRental() throws Exception {
        //when
        CustomerDTO customer = new CustomerDTO("Adam", "Dominik");
        BookDTO book = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerFacade.addCustomer(customer);
        bookFacade.addNewBook(book);
        RentalDTO rental = rentalFacade.rent(
                customer.getCustomerId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(post("/rental/endRental/" + rental.getRentalId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteRental() throws Exception {
        //when
        CustomerDTO customer = new CustomerDTO("Adam", "Dominik");
        BookDTO book = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        customerFacade.addCustomer(customer);
        bookFacade.addNewBook(book);
        RentalDTO rental = rentalFacade.rent(
                customer.getCustomerId(), book.getTitle(),
                book.getAuthor(), LocalDateTime.now()
        );
        //expect
        mockMvc.perform(delete("/rental/delete/" + rental.getRentalId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
