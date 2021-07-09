package com.library.rental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.book.BookNotFoundException;
import com.library.book.NoBookAvailableException;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import com.library.customer.CustomerNotFoundException;
import com.library.rentalHistory.HistoricalRentalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class RentalServiceTest {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private BookFacade bookFacade;
    @Autowired
    private HistoricalRentalRepository historicalRentalRepository;

    @Test
    void shouldAddRentalToDatabase() {
        //given
        BookDTO book1 = new BookDTO("Adam", "Z Nikiszowca", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        bookFacade.addNewBook(book1);
        customerFacade.addCustomer(customer1);
        //when
        RentalDTO rental = rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        //then
        assertThat(rentalService.findRental(rental.getRentalId())).isNotNull();
    }

    @Test
    void shouldSetBookToRented() {
        //given
        BookDTO book1 = new BookDTO("Adam", "Z Nikiszowca", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        bookFacade.addNewBook(book1);
        customerFacade.addCustomer(customer1);
        //when
        final RentalDTO rental1 = rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        //then
        final BookDTO book = bookFacade.findBook(rental1.getBookId());
        assertThat(book.isRented()).isTrue();
    }


    @Test
    void shouldThrowExceptionWhenTryingToAddRentedBookToRental() {
        //given
        BookDTO book1 = new BookDTO("Adam", "Z Nikiszowca", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        bookFacade.addNewBook(book1);
        customerFacade.addCustomer(customer1);
        rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        //when
        Throwable thrown = catchThrowable(() -> rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now()));
        //then
        assertThat(thrown).isInstanceOf(NoBookAvailableException.class);

    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNotInDatabase() {
        //given
        BookDTO book1 = new BookDTO("Adam", "Z Nikiszowca", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        bookFacade.addNewBook(book1);
        //when
        Throwable thrown = catchThrowable(() -> rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now()));
        //then
        assertThat(thrown).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenBookIsNotInDatabase() {
        //given
        BookDTO book1 = new BookDTO("Adam", "Z Nikiszowca", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        customerFacade.addCustomer(customer1);
        //when
        Throwable thrown = catchThrowable(() -> rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now()));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenNoBookIsAvailable() {
        //given
        BookDTO book1 = new BookDTO("Adam", "Z Nikiszowca", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        bookFacade.addNewBook(book1);
        customerFacade.addCustomer(customer1);
        rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        //when
        Throwable thrown = catchThrowable(() -> rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now()));
        //then
        assertThat(thrown).isInstanceOf(NoBookAvailableException.class);

    }

    @Test
    void shouldEndRental() {
        //given
        BookDTO book1 = new BookDTO("Adam", "Z Nikiszowca", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        bookFacade.addNewBook(book1);
        customerFacade.addCustomer(customer1);
        final RentalDTO rental1 = rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        //when
        rentalService.returnBook(rental1.getRentalId(), LocalDateTime.now());
        //then
        assertThat(rentalService.getAllRentals()).isEmpty();
    }

    @Test
    void shouldReturnAllRentals() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");

        BookDTO book2 = new BookDTO("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        CustomerDTO customer2 = new CustomerDTO("Adam", "Dominik");

        bookFacade.addNewBook(book1);
        bookFacade.addNewBook(book2);
        customerFacade.addCustomer(customer1);
        customerFacade.addCustomer(customer2);

        rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        rentalService.rent(customer2.getCustomerId(), book2.getTitle(), book2.getAuthor(), LocalDateTime.now());
        //when
        final List<RentalDTO> allRentals = rentalService.getAllRentals();
        //then
        assertThat(allRentals).hasSize(2);
    }


    @Test
    void shouldFindRentalsOfCustomer() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");
        BookDTO book2 = new BookDTO("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        CustomerDTO customer2 = new CustomerDTO("Adam", "Dominik");
        bookFacade.addNewBook(book1);
        bookFacade.addNewBook(book2);
        customerFacade.addCustomer(customer1);
        customerFacade.addCustomer(customer2);
        final RentalDTO rental1 = rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        rentalService.rent(customer2.getCustomerId(), book2.getTitle(), book2.getAuthor(), LocalDateTime.now());
        //when
        final List<RentalDTO> rentalByCustomer = rentalService.getRentalsOfCustomer(customer1.getCustomerId());
        //than
        assertThat(rentalByCustomer).hasSize(1);
        final RentalDTO rentalFromDB = rentalByCustomer.get(0);
        assertThat(rentalFromDB.getRentalId()).isEqualTo(rental1.getRentalId());
        assertThat(rentalFromDB.getCustomerId()).isEqualTo(rental1.getCustomerId());
        assertThat(rentalFromDB.getBookId()).isEqualTo(rental1.getBookId());
    }

    @Test
    void shouldFindRentalsOfBook() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");

        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        CustomerDTO customer2 = new CustomerDTO("Adam", "Dominik");

        bookFacade.addNewBook(book1);
        customerFacade.addCustomer(customer1);
        customerFacade.addCustomer(customer2);
        final RentalDTO rental1 = rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        rentalService.returnBook(rental1.getRentalId(), LocalDateTime.now());
        final RentalDTO rental2 = rentalService.rent(customer2.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        //when
        final List<RentalDTO> rentalByBook = rentalService.getRentalsOfBook(book1.getBookId());
        //than
        assertThat(rentalByBook).hasSize(1);
        final RentalDTO rentalFromDB = rentalByBook.get(0);
        assertThat(rentalFromDB.getRentalId()).isEqualTo(rental2.getRentalId());
    }

    @Test
    public void shouldFindRental() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");

        bookFacade.addNewBook(book1);
        customerFacade.addCustomer(customer1);
        final RentalDTO rental1 = rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        //when
        RentalDTO rental = rentalService.findRental(rental1.getRentalId());
        //then
        assertThat(rental.getRentalId()).isEqualTo(rental1.getRentalId());
    }

    @Test
    public void shouldThrowExceptionWhenRentalIsNotFound() {
        //when
        Throwable thrown = catchThrowable(() -> rentalService.findRental(UUID.randomUUID()));
        //then
        assertThat(thrown).isInstanceOf(RentalNotFoundException.class);
    }

    @Test
    void shouldDeleteRental() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");

        bookFacade.addNewBook(book1);
        customerFacade.addCustomer(customer1);
        final RentalDTO rental1 = rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        //when
        rentalService.deleteRental(rental1.getRentalId());
        //then
        assertThat(rentalService.getAllRentals()).isEmpty();
    }

    @Test
    public void shouldThrowExceptionWhenTryingToDeleteRentalThatDoesNotExist() {
        Throwable thrown = catchThrowable(() -> rentalService.deleteRental(UUID.randomUUID()));
        //then
        assertThat(thrown).isInstanceOf(RentalNotFoundException.class);
    }

    @Test
    public void shouldThrowExceptionWhenExceedingNumberOfRentals() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");
        BookDTO book2 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");
        BookDTO book3 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");
        BookDTO book4 = new BookDTO("Adam z Nikiszowca", "Adam Domnik", "123456789");
        CustomerDTO customer1 = new CustomerDTO("Łukasz", "Gryziewicz");
        bookFacade.addNewBook(book1);
        bookFacade.addNewBook(book2);
        bookFacade.addNewBook(book3);
        bookFacade.addNewBook(book4);
        customerFacade.addCustomer(customer1);
        //when
        rentalService.rent(customer1.getCustomerId(), book1.getTitle(), book1.getAuthor(), LocalDateTime.now());
        rentalService.rent(customer1.getCustomerId(), book2.getTitle(), book2.getAuthor(), LocalDateTime.now());
        rentalService.rent(customer1.getCustomerId(), book2.getTitle(), book3.getAuthor(), LocalDateTime.now());
        Throwable thrown = catchThrowable(() -> rentalService.rent(customer1.getCustomerId(), book4.getTitle(), book4.getAuthor(), LocalDateTime.now()));
        //then
        assertThat(thrown).isInstanceOf(ExceededMaximumNumberOfRentalsException.class);
    }
}

