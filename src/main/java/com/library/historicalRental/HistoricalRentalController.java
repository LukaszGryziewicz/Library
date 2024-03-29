package com.library.historicalRental;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@EnableWebMvc
@RequestMapping("/historical")
class HistoricalRentalController {
    private final HistoricalRentalFacade historicalRentalFacade;

    public HistoricalRentalController(HistoricalRentalFacade historicalRentalFacade) {
        this.historicalRentalFacade = historicalRentalFacade;
    }

    @GetMapping()
    ResponseEntity<List<HistoricalRentalDTO>> getAllHistoricalRentals() {
        List<HistoricalRentalDTO> historicalRentals = historicalRentalFacade.findAllHistoricalRentals();
        return new ResponseEntity<>(historicalRentals, OK);
    }

    @GetMapping("/customer/{customerId}")
    ResponseEntity<List<HistoricalRentalDTO>> getHistoricalRentalsOfCustomer(@PathVariable("customerId") String customerId) {
        List<HistoricalRentalDTO> ofCustomer = historicalRentalFacade.findHistoricalRentalsOfCustomer(customerId);
        return new ResponseEntity<>(ofCustomer, OK);
    }

    @GetMapping("/book/{bookId}")
    ResponseEntity<List<HistoricalRentalDTO>> getHistoricalRentalsOfBook(@PathVariable("bookId") String bookId) {
        List<HistoricalRentalDTO> ofBook = historicalRentalFacade.findHistoricalRentalsOfBook(bookId);
        return new ResponseEntity<>(ofBook, OK);
    }

}
