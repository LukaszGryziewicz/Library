package com.library.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldFindBookByBookId() {
        //given
        Book book = new Book("Hamlet", "William Shakespeare", "123456789");
        book.setBookId(randomUUID().toString());
        bookRepository.save(book);
        //when
        Optional<Book> bookByBookId = bookRepository.findBookByBookId(book.getBookId());
        //then
        assertThat(bookByBookId).isPresent();
    }

    @Test
    void shouldNotFindBookByBookId() {
        //given
        String id = randomUUID().toString();
        //when
        Optional<Book> bookByBookId = bookRepository.findBookByBookId(id);
        //then
        assertThat(bookByBookId).isNotPresent();
    }

    @Test
    void shouldReturnTrueWhenBookExistsById() {
        //given
        Book book = new Book("Hamlet", "William Shakespeare", "123456789");
        book.setBookId(randomUUID().toString());
        bookRepository.save(book);
        //when
        boolean existsByBookId = bookRepository.existsByBookId(book.getBookId());
        //assert
        assertThat(existsByBookId).isTrue();
    }

    @Test
    void shouldReturnFalseWhenBookDoesNotExistById() {
        //given
        String id = randomUUID().toString();
        //when
        boolean existsByBookId = bookRepository.existsByBookId(id);
        //assert
        assertThat(existsByBookId).isFalse();
    }

    @Test
    void shouldDeleteBookByBookId() {
        //given
        Book book = new Book("Hamlet", "William Shakespeare", "123456789");
        book.setBookId(randomUUID().toString());
        bookRepository.save(book);
        //when
        bookRepository.deleteBookByBookId(book.getBookId());
        //then
        List<Book> books = bookRepository.findAll();
        assertThat(books).isEmpty();
    }

    @Test
    void shouldFindFirstAvailableBookByTitleAndAuthor() {
        //given
        Book book = new Book("Hamlet", "William Shakespeare", "123456789");
        book.setRented(true);
        Book book2 = new Book("Hamlet", "William Shakespeare", "123456789");
        bookRepository.save(book);
        bookRepository.save(book2);
        //when
        Optional<Book> firstAvailableBook = bookRepository.
                findTopBookByTitleAndAuthorAndRentedIsFalse(book.getTitle(), book.getAuthor());
        //then
        assertThat(firstAvailableBook).isPresent();
        assertThat(firstAvailableBook.get()).isEqualTo(book2);
    }

    @Test
    void shouldNotFindFirstAvailableBookByTitleAndAuthor() {
        //given
        String title = "Hamlet";
        String author = "William Shakespeare";
        //when
        Optional<Book> firstAvailableBook = bookRepository.
                findTopBookByTitleAndAuthorAndRentedIsFalse(title, author);
        //then
        assertThat(firstAvailableBook).isNotPresent();
    }

    @Test
    void shouldFindBooksByTitleAndAuthor() {
        //given
        Book book = new Book("Hamlet", "William Shakespeare", "123456789");
        book.setRented(true);
        Book book2 = new Book("Hamlet", "William Shakespeare", "123456789");
        bookRepository.save(book);
        bookRepository.save(book2);
        //when
        List<Book> booksByTitleAndAuthor = bookRepository
                .findBooksByTitleAndAuthor(book.getTitle(), book2.getAuthor());
        //then
        assertThat(booksByTitleAndAuthor).containsExactlyInAnyOrder(book, book2);
    }

    @Test
    void shouldReturnTrueWhenBookExistsByTitleAndAuthor() {
        //given
        Book book = new Book("Hamlet", "William Shakespeare", "123456789");
        bookRepository.save(book);
        //when
        boolean existsByBookId = bookRepository
                .existsByTitleAndAuthor(book.getTitle(), book.getAuthor());
        //assert
        assertThat(existsByBookId).isTrue();
    }

    @Test
    void shouldReturnFalseWhenBookDoesNotExistByTitleAndAuthor() {
        //given
        String title = "Hamlet";
        String author = "William Shakespeare";
        //when
        boolean existsByBookId = bookRepository
                .existsByTitleAndAuthor(title, author);
        //assert
        assertThat(existsByBookId).isFalse();
    }
}