package com.library.rental;

import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.customer.CustomerDTO;
import com.library.customer.CustomerFacade;
import com.library.historicalRental.HistoricalRentalDTO;
import com.library.historicalRental.HistoricalRentalFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

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

    private List<RentalDTO> convertListOfHistoricalRentalsToDTO(List<Rental> rentals) {
        return rentals.stream()
                .map(this::convertRentalToDTO)
                .collect(toList());
    }

    @Transactional
    RentalDTO rent(String customerId, String title, String author) {
        customerFacade.checkIfCustomerExistById(customerId);
        checkIfCustomerIsEligibleForRental(customerId);
        bookFacade.findBooksByTitleAndAuthor(title, author);
        final BookDTO availableBook = bookFacade.findFirstAvailableBookByTitleAndAuthor(title, author);
        bookFacade.rentBook(availableBook.getBookId());
        Rental rental = new Rental(randomUUID().toString(), now(), customerId, availableBook.getBookId());
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

    void scanForOverdueRentals() {
        long threeWeeksInSeconds = 1814400;
        LocalDateTime threeWeeksAgo = now().minusSeconds(threeWeeksInSeconds);
        List<Rental> overdueRentals = rentalRepository.findAll().stream()
                .filter(rental -> rental.getTimeOfRental().isBefore(threeWeeksAgo))
                .collect(toList());
        List<String> customersWithOverdueRentals = overdueRentals.stream()
                .map(Rental::getCustomerId)
                .collect(toList());
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
        final LocalDateTime timeOfRentalEnd = LocalDateTime.now();
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
        return convertListOfHistoricalRentalsToDTO(
                rentalRepository.findAll()
        );
    }

    List<RentalDTO> getRentalsOfCustomer(String customerId) {
        customerFacade.checkIfCustomerExistById(customerId);
        return convertListOfHistoricalRentalsToDTO(
                rentalRepository.findRentalsByCustomerId(customerId)
        );
    }

    List<RentalDTO> getRentalsOfBook(String bookId) {
        bookFacade.checkIfBookExistById(bookId);
        return convertListOfHistoricalRentalsToDTO(
                rentalRepository.findRentalsByBookId(bookId)
        );
    }

}
