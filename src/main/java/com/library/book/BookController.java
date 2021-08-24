package com.library.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping("/books")
class BookController {
    private final BookFacade bookFacade;

    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @GetMapping()
    ResponseEntity<List<BookDTO>> getAllBooks() {
        final List<BookDTO> books = bookFacade.getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<BookDTO> addNewBook(@RequestBody BookDTO book) {
        final BookDTO newBook = bookFacade.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<BookDTO> findBookById(@PathVariable("id") String bookId) {
        BookDTO bookById = bookFacade.findBook(bookId);
        return new ResponseEntity<>(bookById, HttpStatus.OK);
    }

    @GetMapping("/{title}/{author}")
    ResponseEntity<List<BookDTO>> findBooksByTitleAndAuthor(@PathVariable("title") String title, @PathVariable("author") String author) {
        List<BookDTO> booksByTitleAndAuthor = bookFacade.findBooksByTitleAndAuthor(title, author);
        return new ResponseEntity<>(booksByTitleAndAuthor, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<BookDTO> updateBook(@PathVariable("id") String bookId, @RequestBody BookDTO book) {
        BookDTO updatedBook = bookFacade.updateBook(bookId, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBook(@PathVariable("id") String bookId) {
        bookFacade.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
