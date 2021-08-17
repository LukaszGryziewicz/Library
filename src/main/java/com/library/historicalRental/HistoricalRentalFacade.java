package com.library.historicalRental;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoricalRentalFacade {

    private final HistoricalRentalService historicalRentalService;

    public HistoricalRentalFacade(HistoricalRentalService historicalRentalService) {
        this.historicalRentalService = historicalRentalService;
    }

    public void createHistoricalRental(HistoricalRentalDTO historicalRentalDTO) {
        historicalRentalService.createHistoricalRental(historicalRentalDTO);
    }

    List<HistoricalRentalDTO> findAllHistoricalRentals() {
        return historicalRentalService.findAllHistoricalRentals();
    }

    List<HistoricalRentalDTO> findHistoricalRentalsOfCustomer(String customerId) {
        return historicalRentalService.findHistoricalRentalsOfCustomer(customerId);
    }

    List<HistoricalRentalDTO> findHistoricalRentalsOfBook(String bookId) {
        return historicalRentalService.findHistoricalRentalsOfBook(bookId);
    }
}
