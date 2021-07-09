package com.library.rental;

import com.library.book.Book;
import com.library.book.BookDTO;
import com.library.book.BookFacade;
import com.library.customer.CustomerFacade;
import com.library.rentalHistory.HistoricalRentalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private static final int MAX_ALLOWED_RENTALS = 3;
    private final RentalRepository rentalRepository;
    private final HistoricalRentalRepository historicalRentalRepository;
    private final BookFacade bookFacade;
    private final CustomerFacade customerFacade;

    public RentalService(RentalRepository rentalRepository,
                         HistoricalRentalRepository historicalRentalRepository,
                         BookFacade bookFacade,
                         CustomerFacade customerFacade) {
        this.rentalRepository = rentalRepository;
        this.bookFacade = bookFacade;
        this.customerFacade = customerFacade;
        this.historicalRentalRepository = historicalRentalRepository;
    }

    private RentalDTO convertRentalToDTO(Rental rental) {
        return new RentalDTO(
                rental.getRentalId(),
                rental.getCustomerId(),
                rental.getBookId()
        );
    }

    public RentalDTO rent(UUID customerId, String title, String author, LocalDateTime dateOfRental) throws ExceededMaximumNumberOfRentalsException {
        customerFacade.findCustomer(customerId);
        checkIfCustomerIsEligibleForRental(customerId);
        bookFacade.findBooksByTitleAndAuthor(title, author);
        final BookDTO availableBook = bookFacade.findFirstAvailableBookByTitleAndAuthor(title, author);
        final Book book = bookFacade.covertDTOToBook(availableBook);
        bookFacade.rentBook(book.getBookId());
        Rental rental = new Rental(UUID.randomUUID(), customerId, availableBook.getBookId());
        rentalRepository.save(rental);
        return convertRentalToDTO(rental);
    }

    private void checkIfCustomerIsEligibleForRental(UUID customerId) {
        if (booksRentedByCustomer(customerId) >= MAX_ALLOWED_RENTALS) {
            throw new ExceededMaximumNumberOfRentalsException();
        }
    }

    private int booksRentedByCustomer(UUID customerId) {
        return rentalRepository.countRentalsByCustomerId(customerId);
    }

    public RentalDTO findRental(UUID rentalId) {
        final Rental rental = rentalRepository.findRentalByRentalId(rentalId)
                .orElseThrow(RentalNotFoundException::new);
        return convertRentalToDTO(rental);
    }

    @Transactional
    public void returnBook(UUID rentalId, LocalDateTime dateOfReturn) {
        final RentalDTO rental = findRental(rentalId);
        bookFacade.returnBook(rental.getBookId());
        rentalRepository.deleteRentalByRentalId(rental.getRentalId());
    }

    void deleteRental(UUID rentalId) {
        checkIfRentalExists(rentalId);
        rentalRepository.deleteRentalByRentalId(rentalId);
    }

    public void checkIfRentalExists(UUID rentalId) {
        if (!rentalRepository.existsByRentalId(rentalId)) {
            throw new RentalNotFoundException();
        }
    }

    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(this::convertRentalToDTO)
                .collect(Collectors.toList());
    }

    public List<RentalDTO> getRentalsOfCustomer(UUID customerId) {
        return rentalRepository.findRentalsByCustomerId(customerId)
                .stream()
                .map(this::convertRentalToDTO)
                .collect(Collectors.toList());
    }

    public List<RentalDTO> getRentalsOfBook(UUID bookId) {
        return rentalRepository.findRentalByBookId(bookId)
                .stream()
                .map(this::convertRentalToDTO)
                .collect(Collectors.toList());
    }

}
