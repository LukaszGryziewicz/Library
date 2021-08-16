package com.library.historicalRental;

import com.library.book.BookFacade;
import com.library.customer.CustomerFacade;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class HistoricalRentalService {

    private final HistoricalRentalRepository historicalRentalRepository;
    private final CustomerFacade customerFacade;
    private final BookFacade bookFacade;

    public HistoricalRentalService(HistoricalRentalRepository historicalRentalRepository, CustomerFacade customerFacade, BookFacade bookFacade) {
        this.historicalRentalRepository = historicalRentalRepository;
        this.customerFacade = customerFacade;
        this.bookFacade = bookFacade;
    }

    private HistoricalRentalDTO convertHistoricalRentalToDTO(HistoricalRental historicalRental) {
        return new HistoricalRentalDTO(
                historicalRental.getHistoricalRentalId(),
                historicalRental.getDateCreated(),
                historicalRental.getDateEnded(),
                historicalRental.getCustomerId(),
                historicalRental.getFirstName(),
                historicalRental.getLastName(),
                historicalRental.getBookId(),
                historicalRental.getTitle(),
                historicalRental.getAuthor(),
                historicalRental.getIsbn()
        );
    }

    private HistoricalRental convertDTOToHistoricalRental(HistoricalRentalDTO historicalRentalDTO) {
        return new HistoricalRental(
                historicalRentalDTO.getHistoricalRentalId(),
                historicalRentalDTO.getDateCreated(),
                historicalRentalDTO.getDateEnded(),
                historicalRentalDTO.getCustomerId(),
                historicalRentalDTO.getFirstName(),
                historicalRentalDTO.getLastName(),
                historicalRentalDTO.getBookId(),
                historicalRentalDTO.getTitle(),
                historicalRentalDTO.getAuthor(),
                historicalRentalDTO.getIsbn()
        );
    }

    private List<HistoricalRentalDTO> convertListOfHistoricalRentalsToDTO(List<HistoricalRental> historicalRentals) {
        return historicalRentals.stream()
                .map(this::convertHistoricalRentalToDTO)
                .collect(toList());
    }

    public HistoricalRentalDTO createHistoricalRental(HistoricalRentalDTO historicalRentalDTO) {
        historicalRentalRepository.save(convertDTOToHistoricalRental(historicalRentalDTO));
        return historicalRentalDTO;
    }

    List<HistoricalRentalDTO> findAllHistoricalRentals() {
        return convertListOfHistoricalRentalsToDTO(
                historicalRentalRepository.findAll()
        );
    }

    List<HistoricalRentalDTO> findHistoricalRentalsOfCustomer(String customerId) {
        customerFacade.checkIfCustomerExistById(customerId);
        return convertListOfHistoricalRentalsToDTO(
                historicalRentalRepository.findHistoricalRentalsByCustomerId(customerId)
        );
    }

    List<HistoricalRentalDTO> findHistoricalRentalsOfBook(String bookId) {
        bookFacade.checkIfBookExistById(bookId);
        return convertListOfHistoricalRentalsToDTO(
                historicalRentalRepository.findHistoricalRentalsByBookId(bookId)
        );
    }
}
