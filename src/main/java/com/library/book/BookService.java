package com.library.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {

    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    List<Book> getBooks() {
        return bookRepository.findAll();
    }

    Book addNewBook(Book book) {
        return bookRepository.save(book);
    }

    BookDTO convertBookToDTO(Book book) {
        return new BookDTO(book.getTitle(), book.getAuthor(), book.getIsbn());
    }

    Book covertDTOToBook(BookDTO bookDTO) {
        return new Book(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getIsbn());
    }

    Book findBook(Long id) {
        return bookRepository.findBookById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    List<Book> findBooksByTitleAndAuthor(String title, String author) {
        List<Book> booksByTitleAndAuthor = bookRepository.findBooksByTitleAndAuthor(title, author);
        if (booksByTitleAndAuthor.isEmpty()) {
            throw new BookNotFoundException();
        }
        return booksByTitleAndAuthor;
    }

    Book findFirstAvailableBookByTitleAndAuthor(String title, String author) {
        return bookRepository.findTopBookByTitleAndAuthorAndRentedIsFalse(title, author)
                .orElseThrow(NoBookAvailableException::new);
    }

    Book updateBook(Long id, Book newBook) {
        final Book existingBook = findBook(id);
        existingBook.update(newBook);
        bookRepository.save(existingBook);
        return existingBook;
    }


    void deleteBook(Long id) {
        checkIfBookExistById(id);
        bookRepository.deleteById(id);
    }

    void checkIfBookExistById(Long id) {
        final boolean exists = bookRepository.existsById(id);
        if (!exists) {
            throw new BookNotFoundException();
        }
    }
}
