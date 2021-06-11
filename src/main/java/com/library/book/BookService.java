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
        Optional<Book> bookByTitleAndAuthor = bookRepository.findBookById(book.getId());

        if ( bookByTitleAndAuthor.isPresent() ) {
            throw new IllegalStateException("Book already exists");
        }
        return bookRepository.save(book);
    }

    public Book findBook(Long id) {
        return bookRepository.findBookById(id)
                .orElseThrow(() -> new IllegalStateException("Book not found"));
    }

    public Book updateBook(Long id,Book book) {
        Optional<Book> bookById = bookRepository.findBookById(id);
        bookById.orElseThrow(() -> new IllegalStateException("Can't find the book that you want to update"));

        Optional<Book> bookByTitleAndAuthor = bookRepository.findBookByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if ( bookByTitleAndAuthor.isPresent() ){
            throw new IllegalStateException("Book already exists");
        }

        Book existingBook = bookById.get();
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.findBookById(id)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        bookRepository.deleteById(id);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
