package com.library.book;

import com.library.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldAddNewBookToDatabase() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        bookService.addNewBook(book1);
        //then
        assertThat(bookRepository.findAll()).containsExactlyInAnyOrder(book1);
    }

    @Test
    void shouldFindAllBooksInDatabase() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        bookRepository.saveAll(Arrays.asList(book1, book2));
        //when
        List<Book> bookList = bookService.getBooks();
        //then
        assertThat(bookList).containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    void shouldDeleteBookFromDatabase() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookRepository.save(book1);
        //when
        bookService.deleteBook(book1.getId());
        //then
        assertThat(bookRepository.findAll()).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingBookThatDoesNotExist() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        Throwable thrown = catchThrowable(() ->
                bookService.deleteBook(book1.getId()));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void shouldFindBookById() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookRepository.save(book1);
        //when
        Book bookById = bookService.findBook(book1.getId());
        //than
        assertThat(bookById).isEqualTo(book1);
    }

    @Test
    void shouldThrowExceptionWhenFindingBookThatDoesNotExist() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        Throwable thrown = catchThrowable(() ->
                bookService.findBook(book1.getId()));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void shouldUpdateBook() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        bookRepository.save(book1);
        //when
        bookService.updateBook(book1.getId(), book2);
        //then
        assertThat(book1.getTitle()).isEqualTo(book2.getTitle());
        assertThat(book1.getAuthor()).isEqualTo(book2.getAuthor());
    }

    @Test
    void shouldUpdateBook2() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        bookRepository.save(book1);
        //when
        bookService.updateBook2(book1.getId(), book2);
        //then
        assertThat(bookRepository.findAll()).doesNotContain(book1);
        assertThat(bookRepository.findBooksByTitleAndAuthor(book2.getTitle(), book2.getAuthor()))
                .isNotEmpty();
    }

    @Test
    void shouldThrowExceptionWhenUpdatingBookThatDoesNotExist() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        //when
        Throwable thrown = catchThrowable(() ->
                bookService.updateBook(book1.getId(), book2));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenListOfBooksWithGivenTitleAndAuthorIsEmpty() {
        //given
        Book book1 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        Throwable thrown = catchThrowable(() ->
                bookService.findBooksByTitleAndAuthor(book1.getTitle(), book1.getAuthor()));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }
}
