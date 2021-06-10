package com.library.book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addNewBook(Book book) {
        Optional<Book> bookByTitleAndAuthor = bookRepository.findBookByTitleAndAuthor(book.getTitle(), book.getAuthor());

        if (bookByTitleAndAuthor.isPresent()) {
            throw new IllegalStateException("Book already exists");
        }
        bookRepository.save(book);

        return book;
    }

    public void deleteBook(Book book) {
        Optional<Book> bookByTitleAndAuthor = bookRepository.findBookByTitleAndAuthor(book.getTitle(), book.getAuthor());

        if (bookByTitleAndAuthor.isEmpty()) {
            throw new IllegalStateException("Book does not exist");
        }
        bookRepository.delete(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
