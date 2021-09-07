package com.library.rental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.book.BookNotFoundException;
import com.library.book.NoBookAvailableException;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import com.library.customer.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class RentalServiceTest {

    @Autowired
    private RentalFacade rentalFacade;
    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private BookFacade bookFacade;

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

    private RentalDTO createRental(CustomerDTO customer, BookDTO book) {
        return rentalFacade.rent(customer.getCustomerId(), book.getTitle(), book.getAuthor());
    }

    @Test
    void shouldAddRentalToDatabase() {
        //given
        CustomerDTO customer = createCustomer("Will", "Smith");
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //when
        RentalDTO rental = createRental(customer, book);
        //then
        RentalDTO facadeRental = rentalFacade.findRental(rental.getRentalId());
        assertThat(facadeRental).isEqualTo(rental);
    }

    @Test
    void shouldSetBookToRented() {
        //given
        CustomerDTO customer = createCustomer("Will", "Smith");
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //when
        final RentalDTO rental = createRental(customer, book);
        //then
        BookDTO rentedBook = bookFacade.findBook(rental.getBookId());
        assertThat(rentedBook.isRented()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNotInDatabase() {
        //given
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        CustomerDTO customer = new CustomerDTO("Will", "Smith");
        //when
        Throwable thrown = catchThrowable(() -> createRental(customer, book));
        //then
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenBookIsNotInDatabase() {
        //given
        CustomerDTO customer = createCustomer("Will", "Smith");
        BookDTO book = new BookDTO("Hamlet", "William Shakespeare", "123456789");
        //when
        Throwable thrown = catchThrowable(() -> createRental(customer, book));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenNoBookIsAvailable() {
        //given
        CustomerDTO customer = createCustomer("Will", "Smith");
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        createRental(customer, book);
        //when
        Throwable thrown = catchThrowable(() -> createRental(customer, book));
        //then
        assertThat(thrown).isInstanceOf(NoBookAvailableException.class);
    }

    @Test
    public void shouldThrowExceptionWhenCustomerExceededNumberOfRentals() {
        //given
        CustomerDTO customer = createCustomer("Will", "Smith");
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = createBook("Odyssey", "Homer", "987654321");
        BookDTO book3 = createBook("1984", "George Orwell", "786549265");
        BookDTO book4 = createBook("Lord of the Flies", "William Golding", "453628426");
        createRental(customer, book1);
        createRental(customer, book2);
        createRental(customer, book3);
        //when
        Throwable thrown = catchThrowable(() -> createRental(customer, book4));
        //then
        assertThat(thrown).isInstanceOf(ExceededMaximumNumberOfRentalsException.class)
                .hasMessageContaining("Customer reached the maximum number of rentals(3)");
    }

    @Test
    void shouldEndRental() {
        //given
        CustomerDTO customer = createCustomer("Will", "Smith");
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        RentalDTO rental = createRental(customer, book);
        //when
        rentalFacade.endRental(rental.getRentalId());
        //then
        List<RentalDTO> rentalList = rentalFacade.getAllRentals();
        assertThat(rentalList).isEmpty();
    }

    @Test
    void shouldReturnAllRentals() {
        //given
        CustomerDTO customer1 = createCustomer("Will", "Smith");
        CustomerDTO customer2 = createCustomer("Adam", "Dominik");
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = createBook("Odyssey", "Homer", "987654321");
        RentalDTO rental1 = createRental(customer1, book1);
        RentalDTO rental2 = createRental(customer2, book2);
        //when
        final List<RentalDTO> allRentals = rentalFacade.getAllRentals();
        //then
        assertThat(allRentals).containsExactlyInAnyOrder(rental1, rental2);
    }

    @Test
    void shouldFindRentalsOfCustomer() {
        //given
        CustomerDTO customer1 = createCustomer("Will", "Smith");
        CustomerDTO customer2 = createCustomer("Adam", "Dominik");
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = createBook("Odyssey", "Homer", "987654321");
        RentalDTO rental1 = createRental(customer1, book1);
        createRental(customer2, book2);
        //when
        final List<RentalDTO> rentalsOfCustomer = rentalFacade.getRentalsOfCustomer(customer1.getCustomerId());
        //then
        assertThat(rentalsOfCustomer).hasSize(1);
        final RentalDTO rentalFromDB = rentalsOfCustomer.get(0);
        assertThat(rentalFromDB).isEqualTo(rental1);
    }

    @Test
    void shouldFindRentalsOfBook() {
        //given
        CustomerDTO customer1 = createCustomer("Will", "Smith");
        CustomerDTO customer2 = createCustomer("Adam", "Dominik");
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = createBook("Odyssey", "Homer", "987654321");
        RentalDTO rental = createRental(customer1, book1);
        createRental(customer2, book2);
        //when
        final List<RentalDTO> rentalsOfBook = rentalFacade.getRentalsOfBook(book1.getBookId());
        //then
        assertThat(rentalsOfBook).hasSize(1);
        final RentalDTO rentalFromDB = rentalsOfBook.get(0);
        assertThat(rentalFromDB).isEqualTo(rental);
    }

    @Test
    public void shouldFindRental() {
        //given
        CustomerDTO customer = createCustomer("Will", "Smith");
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        RentalDTO rental = createRental(customer, book);
        //when
        RentalDTO facadeRental = rentalFacade.findRental(rental.getRentalId());
        //then
        assertThat(facadeRental).isEqualTo(rental);
    }

    @Test
    public void shouldThrowExceptionWhenRentalIsNotFound() {
        //given
        String randomId = randomUUID().toString();
        //when
        Throwable thrown = catchThrowable(() -> rentalFacade.findRental(randomId));
        //then
        assertThat(thrown).isInstanceOf(RentalNotFoundException.class);
    }

    @Test
    void shouldDeleteRental() {
        //given
        CustomerDTO customer = createCustomer("Will", "Smith");
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        RentalDTO rental = createRental(customer, book);
        //when
        rentalFacade.deleteRental(rental.getRentalId());
        //then
        List<RentalDTO> allRentals = rentalFacade.getAllRentals();
        assertThat(allRentals).isEmpty();
    }

    @Test
    public void shouldThrowExceptionWhenTryingToDeleteRentalThatDoesNotExist() {
        //given
        String randomId = randomUUID().toString();
        //when
        Throwable thrown = catchThrowable(() -> rentalFacade.deleteRental(randomId));
        //then
        assertThat(thrown).isInstanceOf(RentalNotFoundException.class);
    }
}

