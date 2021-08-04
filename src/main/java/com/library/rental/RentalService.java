package com.library.rental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import com.library.rentalHistory.HistoricalRentalRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class RentalService {

    private static final int MAX_ALLOWED_RENTALS = 3;
    private final RentalRepository rentalRepository;
    private final HistoricalRentalRepository historicalRentalRepository;
    private final BookFacade bookFacade;
    private final CustomerFacade customerFacade;

    RentalService(RentalRepository rentalRepository,
                  HistoricalRentalRepository historicalRentalRepository,
                  BookFacade bookFacade,
                  CustomerFacade customerFacade) {
        this.rentalRepository = rentalRepository;
        this.bookFacade = bookFacade;
        this.customerFacade = customerFacade;
        this.historicalRentalRepository = historicalRentalRepository;
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

    RentalDTO rent(String customerId, String title, String author) throws ExceededMaximumNumberOfRentalsException {
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

    void returnBook(String rentalId) {
        final RentalDTO rental = findRental(rentalId);
        bookFacade.returnBook(rental.getBookId());
        rentalRepository.deleteRentalByRentalId(rental.getRentalId());
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
