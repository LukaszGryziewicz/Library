package com.library.book;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookFacade {
    private final BookService bookService;

    public BookDTO convertBookToDTO(Book book) {
        return bookService.convertBookToDTO(book);
    }

    public Book covertDTOToBook(BookDTO bookDTO) {
        return bookService.covertDTOToBook(bookDTO);
    }

    BookFacade(BookService bookService) {
        this.bookService = bookService;
    }

    List<BookDTO> getBooks() {
        return bookService.getBooks();
    }

    public BookDTO addNewBook(BookDTO book) {
        return bookService.addNewBook(book);
    }

    public BookDTO findBook(String bookId) {
        return bookService.findBook(bookId);
    }

    public List<BookDTO> findBooksByTitleAndAuthor(String title, String author) {
        return bookService.findBooksByTitleAndAuthor(title, author);
    }

    public BookDTO findFirstAvailableBookByTitleAndAuthor(String title, String author) {
        return bookService.findFirstAvailableBookByTitleAndAuthor(title, author);
    }

    public BookDTO updateBook(String bookId, BookDTO newBook) {
        return bookService.updateBook(bookId, newBook);
    }

    void deleteBook(String bookId) {
        bookService.deleteBook(bookId);
    }

    public void checkIfBookExistById(String bookId) {
        bookService.checkIfBookExistById(bookId);
    }

    public void returnBook(String bookId) {
        bookService.returnBook(bookId);
    }

    public void rentBook(String bookId) {
        bookService.rentBook(bookId);
    }


}
