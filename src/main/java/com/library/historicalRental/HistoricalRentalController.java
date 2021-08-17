package com.library.historicalRental;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping("/historical")
public class HistoricalRentalController {
    private final HistoricalRentalService historicalRentalService;

    public HistoricalRentalController(HistoricalRentalService historicalRentalService) {
        this.historicalRentalService = historicalRentalService;
    }

    @GetMapping()
    ResponseEntity<List<HistoricalRentalDTO>> getAllHistoricalRentals() {
        List<HistoricalRentalDTO> historicalRentals = historicalRentalService.findAllHistoricalRentals();
        return new ResponseEntity<>(historicalRentals, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    ResponseEntity<List<HistoricalRentalDTO>> getHistoricalRentalsOfCustomer(@PathVariable("customerId") String customerId) {
        List<HistoricalRentalDTO> ofCustomer = historicalRentalService.findHistoricalRentalsOfCustomer(customerId);
        return new ResponseEntity<>(ofCustomer, HttpStatus.OK);
    }

    @GetMapping("/book/{bookId}")
    ResponseEntity<List<HistoricalRentalDTO>> getHistoricalRentalsOfBook(@PathVariable("bookId") String bookId) {
        List<HistoricalRentalDTO> ofBook = historicalRentalService.findHistoricalRentalsOfBook(bookId);
        return new ResponseEntity<>(ofBook, HttpStatus.OK);
    }

}
