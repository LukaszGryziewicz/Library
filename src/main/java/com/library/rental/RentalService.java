package com.library.rental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import com.library.historicalRental.HistoricalRentalDTO;
import com.library.historicalRental.HistoricalRentalFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class RentalService {

    private static final int MAX_ALLOWED_RENTALS = 3;
    private final RentalRepository rentalRepository;
    private final BookFacade bookFacade;
    private final CustomerFacade customerFacade;
    private final HistoricalRentalFacade historicalRentalFacade;

    RentalService(RentalRepository rentalRepository,
                  BookFacade bookFacade,
                  CustomerFacade customerFacade, HistoricalRentalFacade historicalRentalFacade) {
        this.rentalRepository = rentalRepository;
        this.bookFacade = bookFacade;
        this.customerFacade = customerFacade;
        this.historicalRentalFacade = historicalRentalFacade;
    }

    private RentalDTO convertRentalToDTO(Rental rental) {
        final BookDTO book = bookFacade.findBook(rental.getBookId());
        final CustomerDTO customer = customerFacade.findCustomer(rental.getCustomerId());
        return new RentalDTO(
                rental.getRentalId(),
                rental.getTimeOfRental(),
                rental.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                rental.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn()
        );
    }

    RentalDTO rent(String customerId, String title, String author) {
        customerFacade.findCustomer(customerId);
        checkIfCustomerIsEligibleForRental(customerId);
        bookFacade.findBooksByTitleAndAuthor(title, author);
        final BookDTO availableBook = bookFacade.findFirstAvailableBookByTitleAndAuthor(title, author);
        bookFacade.rentBook(availableBook.getBookId());
        Rental rental = new Rental(UUID.randomUUID().toString(), Instant.now(), customerId, availableBook.getBookId());
        rentalRepository.save(rental);
        return convertRentalToDTO(rental);
    }

    private void checkIfCustomerIsEligibleForRental(String customerId) {
        if (booksRentedByCustomer(customerId) >= MAX_ALLOWED_RENTALS) {
            throw new ExceededMaximumNumberOfRentalsException();
        }
    }

    private int booksRentedByCustomer(String customerId) {
        return rentalRepository.countRentalsByCustomerId(customerId);
    }

    RentalDTO findRental(String rentalId) {
        final Rental rental = rentalRepository.findRentalByRentalId(rentalId)
                .orElseThrow(RentalNotFoundException::new);
        return convertRentalToDTO(rental);
    }

    @Transactional
    void endRental(String rentalId) {
        final RentalDTO rental = findRental(rentalId);
        final CustomerDTO customer = customerFacade.findCustomer(rental.getCustomerId());
        final BookDTO book = bookFacade.findBook(rental.getBookId());
        final Instant timeOfRentalEnd = Instant.now();
        HistoricalRentalDTO historicalRentalDTO = new HistoricalRentalDTO(
                rental.getRentalId(),
                rental.getTimeOfRental(),
                timeOfRentalEnd,
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn()
        );
        historicalRentalFacade.createHistoricalRental(historicalRentalDTO);
        rentalRepository.deleteRentalByRentalId(rentalId);
        bookFacade.returnBook(book.getBookId());
    }

    void deleteRental(String rentalId) {
        checkIfRentalExists(rentalId);
        rentalRepository.deleteRentalByRentalId(rentalId);
    }

    private void checkIfRentalExists(String rentalId) {
        if (!rentalRepository.existsByRentalId(rentalId)) {
            throw new RentalNotFoundException();
        }
    }

    List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(this::convertRentalToDTO)
                .collect(Collectors.toList());
    }

    List<RentalDTO> getRentalsOfCustomer(String customerId) {
        return rentalRepository.findRentalsByCustomerId(customerId)
                .stream()
                .map(this::convertRentalToDTO)
                .collect(Collectors.toList());
    }

    List<RentalDTO> getRentalsOfBook(String bookId) {
        return rentalRepository.findRentalByBookId(bookId)
                .stream()
                .map(this::convertRentalToDTO)
                .collect(Collectors.toList());
    }

}
