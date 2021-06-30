package com.library.book;

import com.library.exceptions.BookNotFoundException;
import com.library.exceptions.NoBookAvailableException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book addNewBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findBook(Long id) {
        return bookRepository.findBookById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    public List<Book> findBooksByTitleAndAuthor(String title, String author) {
        List<Book> booksByTitleAndAuthor = bookRepository.findBooksByTitleAndAuthor(title, author);
        if (booksByTitleAndAuthor.isEmpty()) {
            throw new BookNotFoundException();
        }
        return booksByTitleAndAuthor;
    }

    public Book findFirstAvailableBookByTitleAndAuthor(String title, String author) {
        return bookRepository.findTopBookByTitleAndAuthorAndRentedIsFalse(title, author)
                .orElseThrow(NoBookAvailableException::new);
    }

    public Book updateBook(Long id, Book book) {
        final Book existingBook = findBook(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        findBook(id);
        bookRepository.deleteById(id);
    }
}
