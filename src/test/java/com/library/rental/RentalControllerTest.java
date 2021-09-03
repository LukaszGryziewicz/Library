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

    private BookDTO createBook() {
        BookDTO book = new BookDTO("Hamlet", "William Shakespeare", "123456789");
        bookFacade.addBook(book);
        return book;
    }

    private CustomerDTO createCustomer() {
        CustomerDTO customer = new CustomerDTO("Will", "Smith");
        customerFacade.addCustomer(customer);
        return customer;
    }

    private RentalDTO createRental(CustomerDTO customer, BookDTO book) {
        return rentalFacade.rent(customer.getCustomerId(), book.getTitle(), book.getAuthor());
    }

    @Test
    void shouldFindAllRentals() throws Exception {
        //given
        CustomerDTO customer = createCustomer();
        BookDTO book = createBook();
        RentalDTO rental = createRental(customer, book);
        //expect
        mockMvc.perform(get("/rentals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rentalId").value(rental.getRentalId()))
                .andExpect(jsonPath("$[0].customerId").value(rental.getCustomerId()))
                .andExpect(jsonPath("$[0].firstName").value(rental.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(rental.getLastName()))
                .andExpect(jsonPath("$[0].bookId").value(rental.getBookId()))
                .andExpect(jsonPath("$[0].title").value(rental.getTitle()))
                .andExpect(jsonPath("$[0].author").value(rental.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(rental.getIsbn()));
    }

    @Test
    void shouldFindRentalsOfCustomer() throws Exception {
        //given
        CustomerDTO customer1 = createCustomer();
        BookDTO book1 = createBook();
        RentalDTO rental = createRental(customer1, book1);
        //expect
        mockMvc.perform(get("/rentals/customer/" + customer1.getCustomerId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rentalId").value(rental.getRentalId()))
                .andExpect(jsonPath("$[0].customerId").value(rental.getCustomerId()))
                .andExpect(jsonPath("$[0].firstName").value(rental.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(rental.getLastName()))
                .andExpect(jsonPath("$[0].bookId").value(rental.getBookId()))
                .andExpect(jsonPath("$[0].title").value(rental.getTitle()))
                .andExpect(jsonPath("$[0].author").value(rental.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(rental.getIsbn()));
    }

    @Test
    void shouldFindRentalsOfBook() throws Exception {
        //given
        CustomerDTO customer1 = createCustomer();
        BookDTO book = createBook();
        RentalDTO rental1 = createRental(customer1, book);
        //expect
        mockMvc.perform(get("/rentals/book/" + book.getBookId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rentalId").value(rental1.getRentalId()))
                .andExpect(jsonPath("$[0].customerId").value(rental1.getCustomerId()))
                .andExpect(jsonPath("$[0].firstName").value(rental1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(rental1.getLastName()))
                .andExpect(jsonPath("$[0].bookId").value(rental1.getBookId()))
                .andExpect(jsonPath("$[0].title").value(rental1.getTitle()))
                .andExpect(jsonPath("$[0].author").value(rental1.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(rental1.getIsbn()));
    }

    @Test
    void shouldAddRental() throws Exception {
        //when
        CustomerDTO customer = createCustomer();
        BookDTO book = createBook();
        //expect
        mockMvc.perform(post(("/rentals/" + customer.getCustomerId() + "/" + book.getTitle()) + "/" + book.getAuthor()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.bookId").value(book.getBookId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()));
    }

    @Test
    void shouldFindRentalById() throws Exception {
        //when
        CustomerDTO customer = createCustomer();
        BookDTO book = createBook();
        RentalDTO rental = createRental(customer, book);
        //expect
        mockMvc.perform(get("/rentals/" + rental.getRentalId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentalId").value(rental.getRentalId()))
                .andExpect(jsonPath("$.customerId").value(rental.getCustomerId()))
                .andExpect(jsonPath("$.firstName").value(rental.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(rental.getLastName()))
                .andExpect(jsonPath("$.bookId").value(rental.getBookId()))
                .andExpect(jsonPath("$.title").value(rental.getTitle()))
                .andExpect(jsonPath("$.author").value(rental.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(rental.getIsbn()));
    }

    @Test
    void shouldEndRental() throws Exception {
        //when
        CustomerDTO customer = createCustomer();
        BookDTO book = createBook();
        RentalDTO rental = createRental(customer, book);
        //expect
        mockMvc.perform(post("/rentals/end/" + rental.getRentalId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteRental() throws Exception {
        //when
        CustomerDTO customer = createCustomer();
        BookDTO book = createBook();
        RentalDTO rental = createRental(customer, book);
        //expect
        mockMvc.perform(delete("/rentals/" + rental.getRentalId()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
