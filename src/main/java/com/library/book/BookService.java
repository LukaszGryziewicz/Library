package com.library.book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class BookService {

    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    BookDTO convertBookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getBookId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setRented(book.isRented());
        return bookDTO;
    }

    Book covertDTOToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setBookId(bookDTO.getBookId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        return book;
    }

    void returnBook(UUID bookId) {
        final Book book = findBookEntity(bookId);
        book.returnBook();
        bookRepository.save(book);
    }

    void rentBook(UUID bookId) {
        final Book book = findBookEntity(bookId);
        book.rent();
        bookRepository.save(book);
    }

    List<BookDTO> convertListOfBookToDTO(List<Book> listOfBooks) {
        return listOfBooks.stream()
                .map(this::convertBookToDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    List<BookDTO> getBooks() {
        final List<Book> books = bookRepository.findAll();
        return convertListOfBookToDTO(books);
    }

    BookDTO addNewBook(BookDTO bookDTO) {
        bookRepository.save(covertDTOToBook(bookDTO));
        return bookDTO;
    }

    BookDTO findBook(UUID bookId) {
        final Book book = findBookEntity(bookId);
        return convertBookToDTO(book);
    }

    private Book findBookEntity(UUID bookId) {
        return bookRepository.findBookByBookId(bookId)
                .orElseThrow(BookNotFoundException::new);
    }

    List<BookDTO> findBooksByTitleAndAuthor(String title, String author) {
        List<Book> booksByTitleAndAuthor = bookRepository.findBooksByTitleAndAuthor(title, author);
        if (booksByTitleAndAuthor.isEmpty()) {
            throw new BookNotFoundException();
        }
        return convertListOfBookToDTO(booksByTitleAndAuthor);
    }

    BookDTO findFirstAvailableBookByTitleAndAuthor(String title, String author) {
        final Book book = bookRepository.findTopBookByTitleAndAuthorAndRentedIsFalse(title, author)
                .orElseThrow(NoBookAvailableException::new);
        return convertBookToDTO(book);
    }

    BookDTO updateBook(UUID bookId, BookDTO updatedBookDTO) {
        final Book existingBook = findBookEntity(bookId);
        existingBook.update(covertDTOToBook(updatedBookDTO));
        bookRepository.save(existingBook);
        return updatedBookDTO;
    }

    void deleteBook(UUID bookId) {
        checkIfBookExistById(bookId);
        bookRepository.deleteBookByBookId(bookId);
    }

    void checkIfBookExistById(UUID bookId) {
        final boolean exists = bookRepository.existsByBookId(bookId);
        if (!exists) {
            throw new BookNotFoundException();
        }
    }
}
