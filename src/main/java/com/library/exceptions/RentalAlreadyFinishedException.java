package com.library.exceptions;

public class RentalAlreadyFinishedException extends RuntimeException {
    public RentalAlreadyFinishedException() {
        super("Rental already finished");
    }
}
