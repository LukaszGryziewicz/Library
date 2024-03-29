package com.library.historicalRental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import com.library.rental.RentalDTO;
import com.library.rental.RentalFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class HistoricalRentalServiceTest {

    @Autowired
    private CustomerFacade customerFacade;

    @Autowired
    private BookFacade bookFacade;

    @Autowired
    private RentalFacade rentalFacade;

    @Autowired
    private HistoricalRentalFacade historicalRentalFacade;


    private RentalDTO createRental(CustomerDTO customer, BookDTO book) {
        return rentalFacade.rent(customer.getCustomerId(), book.getTitle(), book.getAuthor());
    }

    private BookDTO createBook(String title, String author, String isbn) {
        BookDTO book = new BookDTO(title, author, isbn);
        bookFacade.addBook(book);
        return book;
    }

    private CustomerDTO createCustomer(String firstName, String lastName) {
        CustomerDTO customer = new CustomerDTO(firstName, lastName);
        customerFacade.addCustomer(customer);
        return customer;
    }

    @Test
    void shouldCreateHistoricalRental() {
        //given
        CustomerDTO customer = createCustomer("Adam", "Dominik");
        BookDTO book = createBook("Adam z Nikiszowca", "Adam Dominik", "123456789");
        RentalDTO rental = createRental(customer, book);
        //when
        rentalFacade.endRental(rental.getRentalId());
        //then
        final List<HistoricalRentalDTO> allHistoricalRentals = historicalRentalFacade.findAllHistoricalRentals();
        assertThat(allHistoricalRentals.size()).isEqualTo(1);
        final HistoricalRentalDTO historicalRentalFromDB = allHistoricalRentals.get(0);
        assertThat(historicalRentalFromDB.getHistoricalRentalId()).isEqualTo(rental.getRentalId());
        assertThat(historicalRentalFromDB.getDateCreated()).isEqualTo(rental.getTimeOfRental());
        assertThat(historicalRentalFromDB.getCustomerId()).isEqualTo(rental.getCustomerId());
        assertThat(historicalRentalFromDB.getFirstName()).isEqualTo(rental.getFirstName());
        assertThat(historicalRentalFromDB.getLastName()).isEqualTo(rental.getLastName());
        assertThat(historicalRentalFromDB.getBookId()).isEqualTo(rental.getBookId());
        assertThat(historicalRentalFromDB.getTitle()).isEqualTo(rental.getTitle());
        assertThat(historicalRentalFromDB.getAuthor()).isEqualTo(rental.getAuthor());
        assertThat(historicalRentalFromDB.getIsbn()).isEqualTo(rental.getIsbn());
    }

    @Test
    void shouldFindAllHistoricalRentals() {
        //given
        CustomerDTO customer = createCustomer("Adam", "Dominik");
        BookDTO book = createBook("Adam z Nikiszowca", "Adam Dominik", "123456789");
        RentalDTO rental = createRental(customer, book);
        rentalFacade.endRental(rental.getRentalId());
        CustomerDTO customer2 = createCustomer("Łukasz", "Gryziewicz");
        BookDTO book2 = createBook("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        RentalDTO rental2 = createRental(customer2, book2);
        rentalFacade.endRental(rental2.getRentalId());
        //when
        final List<HistoricalRentalDTO> allHistoricalRentals = historicalRentalFacade.findAllHistoricalRentals();
        //assert
        assertThat(allHistoricalRentals.size()).isEqualTo(2);
    }

    @Test
    void shouldFindHistoricalRentalsOfCustomer() {
        //given
        CustomerDTO customer = createCustomer("Adam", "Dominik");
        BookDTO book = createBook("Adam z Nikiszowca", "Adam Dominik", "123456789");
        RentalDTO rental = createRental(customer, book);
        rentalFacade.endRental(rental.getRentalId());
        CustomerDTO customer2 = createCustomer("Łukasz", "Gryziewicz");
        BookDTO book2 = createBook("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        RentalDTO rental2 = createRental(customer2, book2);
        rentalFacade.endRental(rental2.getRentalId());
        //when
        final List<HistoricalRentalDTO> historicalRentalsOfCustomer = historicalRentalFacade
                .findHistoricalRentalsOfCustomer(customer.getCustomerId());
        //assert
        assertThat(historicalRentalsOfCustomer.size()).isEqualTo(1);
        HistoricalRentalDTO firstHistoricalRentalOfCustomer = historicalRentalsOfCustomer.get(0);
        assertThat(firstHistoricalRentalOfCustomer.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(firstHistoricalRentalOfCustomer.getBookId()).isEqualTo(book.getBookId());
    }


    @Test
    void shouldFindHistoricalRentalsOfBook() {
        //given
        CustomerDTO customer = createCustomer("Adam", "Dominik");
        BookDTO book = createBook("Adam z Nikiszowca", "Adam Dominik", "123456789");
        RentalDTO rental = createRental(customer, book);
        rentalFacade.endRental(rental.getRentalId());
        CustomerDTO customer2 = createCustomer("Łukasz", "Gryziewicz");
        BookDTO book2 = createBook("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        RentalDTO rental2 = createRental(customer2, book2);
        rentalFacade.endRental(rental2.getRentalId());
        //when
        List<HistoricalRentalDTO> historicalRentalsOfBook = historicalRentalFacade
                .findHistoricalRentalsOfBook(book.getBookId());
        //assert
        assertThat(historicalRentalsOfBook.size()).isEqualTo(1);
        HistoricalRentalDTO firstHistoricalRentalOfBook = historicalRentalsOfBook.get(0);
        assertThat(firstHistoricalRentalOfBook.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(firstHistoricalRentalOfBook.getBookId()).isEqualTo(book.getBookId());
    }
}
