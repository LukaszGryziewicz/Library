package com.library.rental;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RentalFacade {

    private final RentalService rentalService;

    public RentalFacade(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public RentalDTO rent(String customerId, String title, String author, LocalDateTime dateOfRental) throws ExceededMaximumNumberOfRentalsException {
        return rentalService.rent(customerId, title, author, dateOfRental);
    }

    public RentalDTO findRental(String rentalId) {
        return rentalService.findRental(rentalId);
    }

    public void returnBook(String rentalId, LocalDateTime dateOfReturn) {
        rentalService.returnBook(rentalId, dateOfReturn);
    }

    void deleteRental(String rentalId) {
        rentalService.deleteRental(rentalId);
    }

    public List<RentalDTO> getAllRentals() {
        return rentalService.getAllRentals();
    }

    public List<RentalDTO> getRentalsOfCustomer(String customerId) {
        return rentalService.getRentalsOfCustomer(customerId);
    }

    public List<RentalDTO> getRentalsOfBook(String bookId) {
        return rentalService.getRentalsOfBook(bookId);
    }
}
