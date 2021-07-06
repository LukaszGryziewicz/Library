package com.library.book;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BookFacade {
    private final BookService bookService;

    BookFacade(BookService bookService) {
        this.bookService = bookService;
    }

    List<BookDTO> getBooks() {
        return bookService.getBooks();
    }

    public BookDTO addNewBook(BookDTO book) {
        return bookService.addNewBook(book);
    }

    public BookDTO findBook(UUID bookId) {
        return bookService.findBook(bookId);
    }

    public List<BookDTO> findBooksByTitleAndAuthor(String title, String author) {
        return bookService.findBooksByTitleAndAuthor(title, author);
    }

    public BookDTO findFirstAvailableBookByTitleAndAuthor(String title, String author) {
        return bookService.findFirstAvailableBookByTitleAndAuthor(title, author);
    }

    BookDTO updateBook(UUID bookId, BookDTO newBook) {
        return bookService.updateBook(bookId, newBook);
    }

    void deleteBook(UUID bookId) {
        bookService.deleteBook(bookId);
    }

    public void checkIfBookExistById(UUID bookId) {
        bookService.checkIfBookExistById(bookId);
    }

}
