package com.library.rental;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rental")
class RentalController {
    private final RentalService rentalService;

    RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping()
    ResponseEntity<List<RentalDTO>> getAllRentalsOfCustomer() {
        final List<RentalDTO> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/customerRentals/{id}")
    ResponseEntity<List<RentalDTO>> getRentalsOfCustomer(@PathVariable("id") UUID customerId) {
        final List<RentalDTO> rentals = rentalService.getRentalsOfCustomer(customerId);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/bookRentals/{id}")
    ResponseEntity<List<RentalDTO>> getRentalsOfBook(@PathVariable("id") UUID customerId) {
        final List<RentalDTO> rentalsOfBook = rentalService.getRentalsOfBook(customerId);
        return new ResponseEntity<>(rentalsOfBook, HttpStatus.OK);
    }

    @PostMapping("/{customerId}/{title}/{author}")
    ResponseEntity<RentalDTO> addNewRental(@PathVariable("customerId") UUID customerId, @PathVariable("title") String title, @PathVariable("author") String author) throws ExceededMaximumNumberOfRentalsException {
        final RentalDTO newRental = rentalService.rent(customerId, title, author, LocalDateTime.now());
        return new ResponseEntity<>(newRental, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<RentalDTO> findRentalById(@PathVariable("id") UUID rentalId) {
        RentalDTO rentalById = rentalService.findRental(rentalId);
        return new ResponseEntity<>(rentalById, HttpStatus.OK);
    }

    @PostMapping("/endRental/{id}")
    ResponseEntity<RentalDTO> endRental(@PathVariable("id") UUID customerId) {
        rentalService.returnBook(customerId, LocalDateTime.now());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteRental(@PathVariable("id") UUID customerId) {
        rentalService.deleteRental(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
