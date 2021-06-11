package com.library.exceptions;

public class BookAlreadyRentedException extends Throwable {
    public BookAlreadyRentedException() {
        super("Book is already rented");
    }
}
