package com.library.rental;

import com.library.book.BookService;
import com.library.customer.CustomerService;
import com.library.exceptions.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Rental createRental(Rental rental) throws ExceededMaximumNumberOfRentalsException, BookAlreadyRentedException, RentalAlreadyFinishedException {

        final boolean containsCustomer = customerService.getCustomers().contains(rental.getCustomer());
        final boolean containsBook = bookService.getBooks().contains(rental.getBook());

        if ( !containsCustomer ) {
            throw new CustomerNotFoundException();
        } else if ( !containsBook ) {
            throw new BookNotFoundException();
        } else if ( rental.isReturned() ) {
            throw new RentalAlreadyFinishedException();
        } else if ( rental.getBook().isRented() ) {
            throw new BookAlreadyRentedException();
        } else if ( rental.getCustomer().getNumberOfRentals() >= 3 ) {
            throw new ExceededMaximumNumberOfRentalsException();
        }

        rental.setReturned(false);
        rental.setTimeOfRental(LocalDateTime.now());
        rental.setTimeOfReturn(null);
        rental.getBook().setRented(true);
        rental.getCustomer().setNumberOfRentals(rental.getCustomer().getNumberOfRentals() + 1);

        return rentalRepository.save(rental);
    }

    public Rental findRental(Long id) {
        return rentalRepository.findRentalById(id)
                .orElseThrow(RentalNotFoundException::new);
    }

    public Rental endRental(Long id) throws RentalAlreadyFinishedException {
        Optional<Rental> rentalById = rentalRepository.findRentalById(id);
        rentalById.orElseThrow(RentalNotFoundException::new);

        Rental rental = rentalById.get();
        if ( rental.isReturned() ) {
            throw new RentalAlreadyFinishedException();
        }
        rental.setReturned(true);
        rental.setTimeOfReturn(LocalDateTime.now());
        rental.getBook().setRented(false);
        rental.getCustomer().setNumberOfRentals(rental.getCustomer().getNumberOfRentals() - 1);
        return rentalRepository.save(rental);

    }

    public void deleteRental(Long id) {
        rentalRepository.findRentalById(id)
                .orElseThrow(RentalNotFoundException::new);

        rentalRepository.deleteById(id);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public List<Rental> getUnfinishedRentals() {
        return rentalRepository.findUnfinishedRentals();
    }

    public List<Rental> getFinishedRentals() {
        return rentalRepository.findFinishedRentals();
    }

    public List<Rental> getRentalsOfCustomer(Long id) {
        return rentalRepository.findRentalByCustomerId(id);
    }

    public List<Rental> getRentalsOfBook(Long id) {
        return rentalRepository.findRentalByBookId(id);
    }

}
