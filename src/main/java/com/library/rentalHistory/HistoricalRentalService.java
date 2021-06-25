package com.library.rentalHistory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricalRentalService {

    private final HistoricalRentalRepository historicalRentalRepository;

    public HistoricalRentalService(HistoricalRentalRepository historicalRentalRepository) {
        this.historicalRentalRepository = historicalRentalRepository;
    }

    List<HistoricalRental>getAllHistoricalRentals() {
        return historicalRentalRepository.findAll();
    }

    List<HistoricalRental>getHistoricalRentalsOfBook(String title, String author){
        return historicalRentalRepository.findHistoricalRentalsByTitleAndAuthor(title, author);
    }

    List<HistoricalRental>getHistoricalRentalsOfCustomer(String firstName, String lastName){
        return historicalRentalRepository.findHistoricalRentalsByFirstNameAndLastName(firstName, lastName);
    }

}
