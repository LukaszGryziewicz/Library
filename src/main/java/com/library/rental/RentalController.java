package com.library.rental;

import com.library.exceptions.ExceededMaximumNumberOfRentalsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping("/rental")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping()
    public ResponseEntity<List<Rental>> getAllRentalsOfCustomer() {
        final List<Rental> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/customerRentals/{id}")
    public ResponseEntity<List<Rental>> getRentalsOfCustomer(Long id) {
        final List<Rental> rentals = rentalService.getRentalsOfCustomer(id);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/bookRentals/{id}")
    public ResponseEntity<List<Rental>> getRentalsOfBook(Long id) {
        final List<Rental> rentalsOfBook = rentalService.getRentalsOfBook(id);
        return new ResponseEntity<>(rentalsOfBook, HttpStatus.OK);
    }

    @PostMapping("/{customerId}/{title}/{author}")
    public ResponseEntity<Rental> addNewRental(@PathVariable("customerId") Long customerId, @PathVariable("title") String title, @PathVariable("author") String author) throws ExceededMaximumNumberOfRentalsException {
        final Rental newRental = rentalService.createRental(customerId, title, author, LocalDateTime.now());
        return new ResponseEntity<>(newRental, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> findRentalById(@PathVariable("id") Long id) {
        Rental rentalById = rentalService.findRental(id);
        return new ResponseEntity<>(rentalById, HttpStatus.OK);
    }

    @PutMapping("/endRental/{id}")
    public ResponseEntity<Rental> endRental(@PathVariable("id") Long id) {
        rentalService.endRental(id, LocalDateTime.now());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRental(@PathVariable("id") Long id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
