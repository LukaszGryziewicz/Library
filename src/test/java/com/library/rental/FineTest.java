package com.library.rental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class FineTest {

    @Autowired
    private RentalFacade rentalFacade;
    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private BookFacade bookFacade;

    @Test
    void shouldAssignAFineToFirstCustomer() {
        //given
        CustomerDTO customer = new CustomerDTO("Adam", "Dominik");
        CustomerDTO customer2 = new CustomerDTO("Łukasz", "Gryziewicz");
        customerFacade.addCustomer(customer);
        customerFacade.addCustomer(customer2);
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        BookDTO book2 = new BookDTO("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        bookFacade.addBook(book1);
        bookFacade.addBook(book2);
        RentalDTO rental = rentalFacade.rent(customer.getCustomerId(), book1.getTitle(), book1.getAuthor());
        long fourWeeksInSeconds = 2419200;
        rental.setTimeOfRental(now().minusSeconds(fourWeeksInSeconds));
        rentalFacade.rent(customer2.getCustomerId(), book2.getTitle(), book2.getAuthor());
        //when
        rentalFacade.scanForOverdueRentals();
        //then
        CustomerDTO facadeCustomer = customerFacade.findCustomer(customer.getCustomerId());
        CustomerDTO facadeCustomer2 = customerFacade.findCustomer(customer2.getCustomerId());
        assertThat(facadeCustomer.getFines().size()).isEqualTo(1);
        assertThat(facadeCustomer2.getFines()).isEmpty();
    }

}
