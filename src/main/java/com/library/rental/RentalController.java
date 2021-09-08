package com.library.rental;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/rentals")
class RentalController {
    private final RentalService rentalService;

    RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping()
    ResponseEntity<List<RentalDTO>> getAllRentalsOfCustomer() {
        final List<RentalDTO> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, OK);
    }

    @GetMapping("/customer/{id}")
    ResponseEntity<List<RentalDTO>> getRentalsOfCustomer(@PathVariable("id") String customerId) {
        final List<RentalDTO> rentals = rentalService.getRentalsOfCustomer(customerId);
        return new ResponseEntity<>(rentals, OK);
    }

    @GetMapping("/book/{id}")
    ResponseEntity<List<RentalDTO>> getRentalsOfBook(@PathVariable("id") String customerId) {
        final List<RentalDTO> rentalsOfBook = rentalService.getRentalsOfBook(customerId);
        return new ResponseEntity<>(rentalsOfBook, OK);
    }

    @PostMapping("/{customerId}/{title}/{author}")
    ResponseEntity<RentalDTO> addNewRental(@PathVariable("customerId") String customerId, @PathVariable("title") String title, @PathVariable("author") String author) throws ExceededMaximumNumberOfRentalsException {
        final RentalDTO newRental = rentalService.rent(customerId, title, author);
        return new ResponseEntity<>(newRental, CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<RentalDTO> findRentalById(@PathVariable("id") String rentalId) {
        RentalDTO rentalById = rentalService.findRental(rentalId);
        return new ResponseEntity<>(rentalById, OK);
    }

    @PostMapping("/end/{id}")
    ResponseEntity<RentalDTO> endRental(@PathVariable("id") String rentalId) {
        rentalService.endRental(rentalId);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRental(@PathVariable("id") String rentalId) {
        rentalService.deleteRental(rentalId);
        return new ResponseEntity<>(OK);
    }
}
