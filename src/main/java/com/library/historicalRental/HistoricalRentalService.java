package com.library.historicalRental;

import org.springframework.stereotype.Service;

@Service
public class HistoricalRentalService {

    private final HistoricalRentalRepository historicalRentalRepository;

    public HistoricalRentalService(HistoricalRentalRepository historicalRentalRepository) {
        this.historicalRentalRepository = historicalRentalRepository;
    }

    HistoricalRentalDTO convertHistoricalRentalToDTO(HistoricalRental historicalRental) {
        return new HistoricalRentalDTO(
                historicalRental.getHistoricalRentalId(),
                historicalRental.getDateCreated(),
                historicalRental.getDateEnded(),
                historicalRental.getCustomerId(),
                historicalRental.getFirstName(),
                historicalRental.getLastName(),
                historicalRental.getTitle(),
                historicalRental.getBookId(),
                historicalRental.getTitle(),
                historicalRental.getIsbn()
        );
    }

    HistoricalRental convertDTOToHistoricalRental(HistoricalRentalDTO historicalRentalDTO) {
        return new HistoricalRental(
                historicalRentalDTO.getHistoricalRentalId(),
                historicalRentalDTO.getDateCreated(),
                historicalRentalDTO.getDateEnded(),
                historicalRentalDTO.getCustomerId(),
                historicalRentalDTO.getFirstName(),
                historicalRentalDTO.getLastName(),
                historicalRentalDTO.getTitle(),
                historicalRentalDTO.getBookId(),
                historicalRentalDTO.getTitle(),
                historicalRentalDTO.getIsbn()
        );
    }

}
