package com.library.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping("/book")
public class BookController {
    private final BookFacade bookFacade;

    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @GetMapping()
    ResponseEntity<List<Book>> getAllBooks() {
        final List<Book> books = bookFacade.getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<Book> addNewBook(@RequestBody Book book) {
        final Book newBook = bookFacade.addNewBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Book> findBookById(@PathVariable("id") Long id) {
        Book bookById = bookFacade.findBook(id);
        return new ResponseEntity<>(bookById, HttpStatus.OK);
    }

    @GetMapping("/{title}/{author}")
    ResponseEntity<List<Book>> findBooksByTitleAndAuthor(@PathVariable("title") String title, @PathVariable("author") String author) {
        List<Book> booksByTitleAndAuthor = bookFacade.findBooksByTitleAndAuthor(title, author);
        return new ResponseEntity<>(booksByTitleAndAuthor, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
        Book updatedBook = bookFacade.updateBook(id, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        bookFacade.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
