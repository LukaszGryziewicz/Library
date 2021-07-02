package com.library.book;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookFacade {
    private final BookService bookService;

    BookFacade(BookService bookService) {
        this.bookService = bookService;
    }

    List<Book> getBooks() {
        return bookService.getBooks();
    }

    public Book addNewBook(Book book) {
        return bookService.addNewBook(book);
    }

    public Book findBook(Long id) {
        return bookService.findBook(id);
    }

    public List<Book> findBooksByTitleAndAuthor(String title, String author) {
        return bookService.findBooksByTitleAndAuthor(title, author);
    }

    public Book findFirstAvailableBookByTitleAndAuthor(String title, String author) {
        return bookService.findFirstAvailableBookByTitleAndAuthor(title, author);
    }

    Book updateBook(Long id, Book newBook) {
        return bookService.updateBook(id, newBook);
    }

    void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

    public void checkIfBookExistById(Long id) {
        bookService.checkIfBookExistById(id);
    }


}
