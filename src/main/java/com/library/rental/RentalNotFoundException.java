package com.library.rental;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException() {
        super("Rental not found");
    }
}
