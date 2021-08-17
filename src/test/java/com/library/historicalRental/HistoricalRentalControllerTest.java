package com.library.historicalRental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import com.library.rental.RentalDTO;
import com.library.rental.RentalFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class HistoricalRentalControllerTest {

    @Autowired
    BookFacade bookFacade;

    @Autowired
    CustomerFacade customerFacade;

    @Autowired
    RentalFacade rentalFacade;

    @Autowired
    MockMvc mockMvc;

    private CustomerDTO createCustomer(String firstName, String lastName) {
        CustomerDTO customer = new CustomerDTO(firstName, lastName);
        return customerFacade.addCustomer(customer);
    }

    private BookDTO createBook(String title, String author, String isbn) {
        BookDTO book = new BookDTO(title, author, isbn);
        return bookFacade.addBook(book);
    }

    private RentalDTO createRental(CustomerDTO customer, BookDTO book) {
        return rentalFacade.rent(customer.getCustomerId(), book.getTitle(), book.getAuthor());
    }

    private void endRental(RentalDTO rental) {
        rentalFacade.endRental(rental.getRentalId());
    }

    @Test
    void shouldFindAllHistoricalRentals() throws Exception {
        //given
        CustomerDTO customer = createCustomer("Adam", "Dominik");
        BookDTO book = createBook("Adam z Nikiszowca", "Adam Dominik", "123456789");
        RentalDTO rental = createRental(customer, book);
        endRental(rental);
        CustomerDTO customer2 = createCustomer("Łukasz", "Gryziewicz");
        BookDTO book2 = createBook("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        RentalDTO rental2 = createRental(customer2, book2);
        endRental(rental2);
        //expect
        mockMvc.perform(get("/historical"))
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$[0].historicalRentalId").value(rental.getRentalId()))
                .andExpect(jsonPath("$[0].customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$[0].firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$[0].bookId").value(book.getBookId()))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$[1].historicalRentalId").value(rental2.getRentalId()))
                .andExpect(jsonPath("$[1].customerId").value(customer2.getCustomerId()))
                .andExpect(jsonPath("$[1].firstName").value(customer2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(customer2.getLastName()))
                .andExpect(jsonPath("$[1].bookId").value(book2.getBookId()))
                .andExpect(jsonPath("$[1].title").value(book2.getTitle()))
                .andExpect(jsonPath("$[1].author").value(book2.getAuthor()))
                .andExpect(jsonPath("$[1].isbn").value(book2.getIsbn()));
    }

    @Test
    void shouldFindHistoricalRentalsByCustomerId() throws Exception {
        //given
        CustomerDTO customer = createCustomer("Adam", "Dominik");
        BookDTO book = createBook("Adam z Nikiszowca", "Adam Dominik", "123456789");
        RentalDTO rental = createRental(customer, book);
        endRental(rental);
        //expect
        mockMvc.perform(get("/historical/customer/" + customer.getCustomerId()))
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$[0].historicalRentalId").value(rental.getRentalId()))
                .andExpect(jsonPath("$[0].customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$[0].firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$[0].bookId").value(book.getBookId()))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()));
    }

    @Test
    void shouldFindHistoricalRentalsByBookId() throws Exception {
        //given
        CustomerDTO customer = createCustomer("Adam", "Dominik");
        BookDTO book = createBook("Adam z Nikiszowca", "Adam Dominik", "123456789");
        RentalDTO rental = createRental(customer, book);
        endRental(rental);
        //expect
        mockMvc.perform(get("/historical/book/" + book.getBookId()))
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$[0].historicalRentalId").value(rental.getRentalId()))
                .andExpect(jsonPath("$[0].customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$[0].firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$[0].bookId").value(book.getBookId()))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()));
    }
}
