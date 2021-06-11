package com.library.exceptions;

public class RentalAlreadyFinishedException extends Throwable {
    public RentalAlreadyFinishedException() {
        super("Rental already finished");
    }
}
