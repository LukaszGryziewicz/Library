package com.library.exceptions;

public class ExceededMaximumNumberOfRentalsException extends Throwable {
    public ExceededMaximumNumberOfRentalsException() {
        super("Customer reached the maximum number of rentals(3)");
    }
}
