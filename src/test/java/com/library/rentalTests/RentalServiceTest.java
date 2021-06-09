package com.library.rentalTests;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.customer.Customer;
import com.library.customer.CustomerRepository;
import com.library.rental.Rental;
import com.library.rental.RentalRepository;
import com.library.rental.RentalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class RentalServiceTest {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldAddRentalToDatabase() {
        //given
        Book book1 = new Book("Adam", "Z Nikiszowca", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        bookRepository.save(book1);
        customerRepository.save(customer1);
        //when
        Rental rental1 = new Rental(customer1, book1);
        rentalService.createRental(rental1);
        //then
        assertThat(rentalRepository.findAll()).containsExactlyInAnyOrder(rental1);
    }

    @Test
    void shouldSetReturnedToFalseAndDateOfRentalToNowAndDateOfReturnToNull() {
        //given
        Book book1 = new Book("Adam", "Z Nikiszowca", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        bookRepository.save(book1);
        customerRepository.save(customer1);
        //when
        Rental rental1 = new Rental(customer1, book1);
        rentalService.createRental(rental1);
        //then
        assertThat(rental1.isReturned()).isFalse();
        assertThat(rental1.getTimeOfRental()).isBetween(LocalDateTime.now().minusSeconds(1), LocalDateTime.now());
        assertThat(rental1.getTimeOfReturn()).matches(Objects::isNull);
    }

    @Test
    void shouldThrowExceptionWhenTryingToAddAlreadyFinishedRental() {
        //given
        Book book1 = new Book("Adam", "Z Nikiszowca", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        bookRepository.save(book1);
        customerRepository.save(customer1);
        //when
        Rental rental1 = new Rental(customer1, book1, true, LocalDateTime.MIN, LocalDateTime.MAX);
        Throwable thrown = catchThrowable(() -> rentalService.createRental(rental1));
        //than
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Rental already finished");
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNotInDatabase() {
        //given
        Book book1 = new Book("Adam", "Z Nikiszowca", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        bookRepository.save(book1);
        Rental rental1 = new Rental(customer1, book1, true, LocalDateTime.MIN, LocalDateTime.MAX);
        //when
        Throwable thrown = catchThrowable(() -> rentalService.createRental(rental1));
        //than
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Could not find customer");
    }

    @Test
    void shouldThrowExceptionWhenBookIsNotInDatabase() {
        //given
        Book book1 = new Book("Adam", "Z Nikiszowca", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        customerRepository.save(customer1);
        Rental rental1 = new Rental(customer1, book1, true, LocalDateTime.MIN, LocalDateTime.MAX);
        //when
        Throwable thrown = catchThrowable(() -> rentalService.createRental(rental1));
        //than
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Could not find book");
    }

    @Test
    void shouldEndRental() {
        //given
        Book book1 = new Book("Adam", "Z Nikiszowca", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        bookRepository.save(book1);
        customerRepository.save(customer1);

        Rental rental1 = new Rental(customer1, book1);
        rentalRepository.save(rental1);
        //when
        rentalService.endRental(rental1);
        //than
        assertThat(rental1.isReturned()).isTrue();
        assertThat(rental1.getTimeOfReturn()).isBetween(LocalDateTime.now().minusSeconds(1), LocalDateTime.now());
    }

    @Test
    void shouldThrowExceptionWhenRentalIsAlreadyFinished() {
        //given
        Book book1 = new Book("Adam", "Z Nikiszowca", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        bookRepository.save(book1);
        customerRepository.save(customer1);
        Rental rental1 = new Rental(customer1, book1);
        rentalRepository.save(rental1);
        //when
        rentalService.endRental(rental1);
        Throwable thrown = catchThrowable(() -> rentalService.endRental(rental1));
        //than
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Rental already finished");
    }

    @Test
    void shouldReturnAllRentals() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Domnik", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        Rental rental1 = new Rental(customer1, book1, false, LocalDateTime.now(), null);

        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        Customer customer2 = new Customer("Adam", "Dominik");
        Rental rental2 = new Rental(customer2, book2, true, LocalDateTime.now(), LocalDateTime.MAX);

        bookRepository.saveAll(Arrays.asList(book1, book2));
        customerRepository.saveAll(Arrays.asList(customer1, customer2));
        rentalRepository.saveAll(Arrays.asList(rental1, rental2));
        //when
        final List<Rental> allRentals = rentalService.getAllRentals();

        assertThat(allRentals).containsExactlyInAnyOrder(rental1, rental2);
    }

    @Test
    void shouldReturnFinishedRentals() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Domnik", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        Rental rental1 = new Rental(customer1, book1, false, LocalDateTime.now(), null);

        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        Customer customer2 = new Customer("Adam", "Dominik");
        Rental rental2 = new Rental(customer2, book2, true, LocalDateTime.now(), LocalDateTime.MAX);

        bookRepository.saveAll(Arrays.asList(book1, book2));
        customerRepository.saveAll(Arrays.asList(customer1, customer2));
        rentalRepository.saveAll(Arrays.asList(rental1, rental2));
        //when
        final List<Rental> finishedRentals = rentalService.getFinishedRentals();

        assertThat(finishedRentals).containsExactlyInAnyOrder(rental2);
    }

    @Test
    void shouldReturnUnfinishedRentals() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Domnik", "123456789");
        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        Rental rental1 = new Rental(customer1, book1, false, LocalDateTime.now(), null);

        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        Customer customer2 = new Customer("Adam", "Dominik");
        Rental rental2 = new Rental(customer2, book2, true, LocalDateTime.now(), LocalDateTime.MAX);

        bookRepository.saveAll(Arrays.asList(book1, book2));
        customerRepository.saveAll(Arrays.asList(customer1, customer2));
        rentalRepository.saveAll(Arrays.asList(rental1, rental2));
        //when
        final List<Rental> unfinishedRentals = rentalService.getUnfinishedRentals();

        assertThat(unfinishedRentals).containsExactlyInAnyOrder(rental1);
    }

    @Test
    void shouldFindRentalsOfCustomer() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Domnik", "123456789");
        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");

        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        Rental rental1 = new Rental(customer1, book1);
        Rental rental2 = new Rental(customer1, book2);

        bookRepository.saveAll(Arrays.asList(book1, book2));
        customerRepository.save(customer1);
        rentalRepository.saveAll(Arrays.asList(rental1, rental2));
        //when
        final List<Rental> rentalByCustomer = rentalService.getRentalsOfCustomer(customer1);
        //than
        assertThat(rentalByCustomer).containsExactlyInAnyOrder(rental1, rental2);
    }

    @Test
    void shouldFindRentalsOfBook() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Domnik", "123456789");

        Customer customer1 = new Customer("Łukasz", "Gryziewicz");
        Customer customer2 = new Customer("Adam", "Dominik");

        Rental rental1 = new Rental(customer1, book1);
        Rental rental2 = new Rental(customer2, book1);
        bookRepository.save(book1);
        customerRepository.saveAll(Arrays.asList(customer1, customer2));
        rentalRepository.saveAll(Arrays.asList(rental1, rental2));
        //when
        final List<Rental> rentalByBook = rentalService.getRentalsOfBook(book1);
        //than
        assertThat(rentalByBook).containsExactlyInAnyOrder(rental1, rental2);
    }

}

