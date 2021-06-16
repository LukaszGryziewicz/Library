package com.library.exceptions;

public class ExceededMaximumNumberOfRentalsException extends RuntimeException {
    public ExceededMaximumNumberOfRentalsException() {
        super("Customer reached the maximum number of rentals(3)");
    }
}
