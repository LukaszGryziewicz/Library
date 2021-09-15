package com.library.book;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookFacade {
    private final BookService bookService;

    public BookFacade(BookService bookService) {
        this.bookService = bookService;
    }

    List<BookDTO> getBooks() {
        return bookService.getBooks();
    }

    public BookDTO addBook(BookDTO book) {
        return bookService.addBook(book);
    }

    public BookDTO findBook(String bookId) {
        return bookService.findBook(bookId);
    }

    public BookDTO findFirstAvailableBookByTitleAndAuthor(String title, String author) {
        return bookService.findFirstAvailableBookByTitleAndAuthor(title, author);
    }

    public void checkIfBookExistById(String bookId) {
        bookService.checkIfBookExistById(bookId);
    }

    public void checkIfBookExistByTitleAndAuthor(String title, String author) {
        bookService.checkIfBookExistByTitleAndAuthor(title, author);
    }

    public void returnBook(String bookId) {
        bookService.returnBook(bookId);
    }

    public void rentBook(String bookId) {
        bookService.rentBook(bookId);
    }

    List<BookDTO> findBooksByTitleAndAuthor(String title, String author) {
        return bookService.findBooksByTitleAndAuthor(title, author);
    }

    BookDTO updateBook(String bookId, BookDTO newBook) {
        return bookService.updateBook(bookId, newBook);
    }

    void deleteBook(String bookId) {
        bookService.deleteBook(bookId);
    }
}
