package com.library.rental;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.customer.Customer;
import com.library.customer.CustomerRepository;
import com.library.exceptions.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;


    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository, CustomerRepository customerRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    public Rental createRental(Long customerId, String title, String author) throws ExceededMaximumNumberOfRentalsException {
        Optional<Customer> customerById = customerRepository.findCustomerById(customerId);
        List<Book> bookByTitleAndAuthor = bookRepository.findBooksByTitleAndAuthor(title, author);
        Optional<Book> availableBook = bookRepository.findTopBookByTitleAndAuthorAndRentedIsFalse(title, author);

        customerById.orElseThrow(CustomerNotFoundException::new);
        if (bookByTitleAndAuthor.isEmpty()) {
            throw new BookNotFoundException();
        }
        availableBook.orElseThrow(NoBookAvailableException::new);
        if (rentalRepository.findRentalsByCustomerId(customerId).size() == 3) {
            throw new ExceededMaximumNumberOfRentalsException();
        }

        Rental rental = new Rental(customerById.get(), availableBook.get());

        rental.setReturned(false);
        rental.setTimeOfRental(LocalDateTime.now());
        rental.setTimeOfReturn(null);
        rental.getBook().setRented(true);

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
        if (rental.isReturned()) {
            throw new RentalAlreadyFinishedException();
        }
        rental.setReturned(true);
        rental.setTimeOfReturn(LocalDateTime.now());
        rental.getBook().setRented(false);
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
        return rentalRepository.findRentalsByCustomerId(id);
    }

    public List<Rental> getRentalsOfBook(Long id) {
        return rentalRepository.findRentalByBookId(id);
    }

}
