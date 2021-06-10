package com.library.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        final List<Book> books = bookService.getBooks();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addNewBook(@RequestBody Book book) {
        final Book newBook = bookService.addNewBook(book);
        return new ResponseEntity<>(newBook,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete//")
    public ResponseEntity<?> deleteBook(@RequestBody Book book) {
        bookService.deleteBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
