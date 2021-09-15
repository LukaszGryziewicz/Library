package com.library.rental;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalFacade {

    private final RentalService rentalService;

    public RentalFacade(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public RentalDTO rent(String customerId, String title, String author) throws ExceededMaximumNumberOfRentalsException {
        return rentalService.rent(customerId, title, author);
    }

    public void endRental(String rentalId) {
        rentalService.endRental(rentalId);
    }

    RentalDTO findRental(String rentalId) {
        return rentalService.findRental(rentalId);
    }

    void deleteRental(String rentalId) {
        rentalService.deleteRental(rentalId);
    }

    List<RentalDTO> getAllRentals() {
        return rentalService.getAllRentals();
    }

    List<RentalDTO> getRentalsOfCustomer(String customerId) {
        return rentalService.getRentalsOfCustomer(customerId);
    }

    List<RentalDTO> getRentalsOfBook(String bookId) {
        return rentalService.getRentalsOfBook(bookId);
    }
}
