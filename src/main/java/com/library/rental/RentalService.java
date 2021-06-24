package com.library.rental;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.customer.Customer;
import com.library.customer.CustomerRepository;
import com.library.exceptions.*;
import com.library.rentalHistory.HistoricalRental;
import com.library.rentalHistory.HistoricalRentalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final HistoricalRentalRepository historicalRentalRepository;


    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository, CustomerRepository customerRepository, HistoricalRentalRepository historicalRentalRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.historicalRentalRepository = historicalRentalRepository;
    }

    public Rental createRental(Long customerId, String title, String author, LocalDateTime dateOfRental) throws ExceededMaximumNumberOfRentalsException {
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

        rental.getBook().setRented(true);

        return rentalRepository.save(rental);
    }

    public Rental findRental(Long id) {
        return rentalRepository.findRentalById(id)
                .orElseThrow(RentalNotFoundException::new);
    }

    public void endRental(Long id, LocalDateTime dateOfReturn){
        Optional<Rental> rentalById = rentalRepository.findRentalById(id);
        final Rental rental = rentalById.orElseThrow(RentalNotFoundException::new);

        rental.getBook().setRented(false);

        HistoricalRental historicalRental = new HistoricalRental(
                rental.getBook().getTitle(),
                rental.getBook().getAuthor(),
                rental.getBook().getIsbn(),
                rental.getCustomer().getFirstName(),
                rental.getCustomer().getLastName());
        historicalRentalRepository.save(historicalRental);

        rentalRepository.delete(rental);
    }

    public void deleteRental(Long id) {
        rentalRepository.findRentalById(id)
                .orElseThrow(RentalNotFoundException::new);

        rentalRepository.deleteById(id);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public List<Rental> getRentalsOfCustomer(Long id) {
        return rentalRepository.findRentalsByCustomerId(id);
    }

    public List<Rental> getRentalsOfBook(Long id) {
        return rentalRepository.findRentalByBookId(id);
    }

}
