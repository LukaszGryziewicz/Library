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

    private BookDTO convertBookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getBookId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setRented(book.isRented());
        return bookDTO;
    }

    private Book covertDTOToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setBookId(bookDTO.getBookId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setRented(bookDTO.isRented());
        return book;
    }

    private List<BookDTO> convertListOfBookToDTO(List<Book> listOfBooks) {
        return listOfBooks.stream()
                .map(this::convertBookToDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    void returnBook(String bookId) {
        final Book book = findBookEntity(bookId);
        book.returnBook();
        bookRepository.save(book);
    }

    void rentBook(String bookId) {
        final Book book = findBookEntity(bookId);
        book.rent();
        bookRepository.save(book);
    }

    List<BookDTO> getBooks() {
        final List<Book> books = bookRepository.findAll();
        return convertListOfBookToDTO(books);
    }

    BookDTO addBook(BookDTO bookDTO) {
        bookDTO.setBookId(UUID.randomUUID().toString());
        bookRepository.save(covertDTOToBook(bookDTO));
        return bookDTO;
    }

    BookDTO findBook(String bookId) {
        final Book book = findBookEntity(bookId);
        return convertBookToDTO(book);
    }

    private Book findBookEntity(String bookId) {
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

    BookDTO updateBook(String bookId, BookDTO updatedBookDTO) {
        final Book existingBook = findBookEntity(bookId);
        existingBook.update(covertDTOToBook(updatedBookDTO));
        bookRepository.save(existingBook);
        return updatedBookDTO;
    }

    void deleteBook(String bookId) {
        checkIfBookExistById(bookId);
        bookRepository.deleteBookByBookId(bookId);
    }

    void checkIfBookExistById(String bookId) {
        final boolean exists = bookRepository.existsByBookId(bookId);
        if (!exists) {
            throw new BookNotFoundException();
        }
    }
}
