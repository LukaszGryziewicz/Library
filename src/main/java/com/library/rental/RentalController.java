package com.library.rental;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    ResponseEntity<List<RentalDTO>> getRentalsOfCustomer(@PathVariable("id") String customerId) {
        final List<RentalDTO> rentals = rentalService.getRentalsOfCustomer(customerId);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/bookRentals/{id}")
    ResponseEntity<List<RentalDTO>> getRentalsOfBook(@PathVariable("id") String customerId) {
        final List<RentalDTO> rentalsOfBook = rentalService.getRentalsOfBook(customerId);
        return new ResponseEntity<>(rentalsOfBook, HttpStatus.OK);
    }

    @PostMapping("/{customerId}/{title}/{author}")
    ResponseEntity<RentalDTO> addNewRental(@PathVariable("customerId") String customerId, @PathVariable("title") String title, @PathVariable("author") String author) throws ExceededMaximumNumberOfRentalsException {
        final RentalDTO newRental = rentalService.rent(customerId, title, author);
        return new ResponseEntity<>(newRental, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<RentalDTO> findRentalById(@PathVariable("id") String rentalId) {
        RentalDTO rentalById = rentalService.findRental(rentalId);
        return new ResponseEntity<>(rentalById, HttpStatus.OK);
    }

    @PostMapping("/endRental/{id}")
    ResponseEntity<RentalDTO> endRental(@PathVariable("id") String rentalId) {
        rentalService.returnBook(rentalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteRental(@PathVariable("id") String rentalId) {
        rentalService.deleteRental(rentalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
