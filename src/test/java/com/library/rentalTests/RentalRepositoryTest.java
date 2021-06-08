package com.library.rentalTests;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.customer.Customer;
import com.library.customer.CustomerRepository;
import com.library.rental.Rental;
import com.library.rental.RentalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RentalRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RentalRepository rentalRepository;

    @Test
    public void shouldFindUnfinishedRentals() {
        //given
        Book book1= new Book("Adam z Nikiszowca","Adam Domnik","123456789");
        Customer customer1= new Customer("Łukasz","Gryziewicz");
        Rental rental1= new Rental(customer1,book1,false, LocalDateTime.now(),null);

        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        Customer customer2= new Customer("Adam","Dominik");
        Rental rental2= new Rental(customer2,book2,true,LocalDateTime.now(),LocalDateTime.MAX);

        bookRepository.saveAll(Arrays.asList(book1,book2));
        customerRepository.saveAll(Arrays.asList(customer1,customer2));
        rentalRepository.saveAll(Arrays.asList(rental1,rental2));
        //when
        List<Rental> currentRentals = rentalRepository.findUnfinishedRentals();
        //then
        assertThat(currentRentals).containsExactlyInAnyOrder(rental1);
    }

    @Test
    public void shouldFindFinishedRentals() {
        //given
        Book book1= new Book("Adam z Nikiszowca","Adam Domnik","123456789");
        Customer customer1= new Customer("Łukasz","Gryziewicz");
        Rental rental1= new Rental(customer1,book1,false, LocalDateTime.now(),null);


        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        Customer customer2= new Customer("Adam","Dominik");
        Rental rental2= new Rental(customer2,book2,true,LocalDateTime.now(),LocalDateTime.MAX);

        bookRepository.saveAll(Arrays.asList(book1,book2));
        customerRepository.saveAll(Arrays.asList(customer1,customer2));
        rentalRepository.saveAll(Arrays.asList(rental1,rental2));
        //when
        List<Rental> finishedRentals = rentalRepository.findFinishedRentals();
        //then
        assertThat(finishedRentals).containsExactlyInAnyOrder(rental2);
    }
}
