package com.library.rental;

import com.library.book.BookService;
import com.library.customer.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    private final BookService bookService;
    private final CustomerService customerService;

    public RentalService(RentalRepository rentalRepository, BookService bookService, CustomerService customerService) {
        this.rentalRepository = rentalRepository;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    public void createRental(Rental rental) {

        final boolean containsCustomer = customerService.getCustomers().contains(rental.getCustomer());
        final boolean containsBook = bookService.getBooks().contains(rental.getBook());

        if ( !containsCustomer ) {
            throw new IllegalStateException("Could not find customer");
        } else if ( !containsBook ) {
            throw new IllegalStateException("Could not find book");
        } else if ( rental.isReturned() ) {
            throw new IllegalStateException("Rental already finished");
        }

        rental.setReturned(false);
        rental.setTimeOfRental(LocalDateTime.now());
        rental.setTimeOfReturn(null);

        rentalRepository.save(rental);
    }

    public void endRental(Rental rental) {
        if ( rental.isReturned() ) {
            throw new IllegalStateException("Rental already finished");
        }
        rental.setReturned(true);
        rental.setTimeOfReturn(LocalDateTime.now());
        rentalRepository.save(rental);
    }

    List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    List<Rental> getUnfinishedRentals() {
        return rentalRepository.findUnfinishedRentals();
    }

    List<Rental> getFinishedRentals() {
        return rentalRepository.findFinishedRentals();
    }


}
