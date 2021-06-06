package com.library.rental;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void rent(Rental rental) {
        rentalRepository.save(rental);
    }

    List<Rental> getAllRentals() {
        return rentalRepository.findAll();

    }
}
