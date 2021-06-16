package com.library.exceptions;

public class NoBookAvailableException extends RuntimeException{
    public NoBookAvailableException() {
        super("All books with given title and author are unavailable");
    }
}
