package com.library.book;

import com.library.exceptions.BookNotFoundException;
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
        return bookRepository.save(book);
    }

    public Book findBook(Long id) {
        return bookRepository.findBookById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    public List<Book> findBooksByTitleAndAuthor(String title, String author) {
        List<Book> booksByTitleAndAuthor = bookRepository.findBooksByTitleAndAuthor(title, author);
        if ( booksByTitleAndAuthor.isEmpty() ) {
            throw new BookNotFoundException();
        }
        return booksByTitleAndAuthor;
    }

    public Book updateBook(Long id, Book book) {
        Optional<Book> bookById = bookRepository.findBookById(id);
        bookById.orElseThrow(BookNotFoundException::new);

        Book existingBook = bookById.get();
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.findBookById(id)
                .orElseThrow(BookNotFoundException::new);

        bookRepository.deleteById(id);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
