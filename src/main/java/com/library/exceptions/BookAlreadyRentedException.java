package com.library.exceptions;

public class BookAlreadyRentedException extends RuntimeException {
    public BookAlreadyRentedException() {
        super("Book is already rented");
    }
}
