package com.library.book;

public class NoBookAvailableException extends RuntimeException {
    public NoBookAvailableException() {
        super("All books with given title and author are unavailable");
    }
}
