package com.library.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class BookServiceTest {

    @Autowired
    BookFacade bookFacade;

    @Test
    void shouldAddNewBookToDatabase() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        bookFacade.addBook(book1);
        //then
        final int bookListSize = bookFacade.findBooksByTitleAndAuthor(book1.getTitle(), book1.getAuthor()).size();
        assertThat(bookListSize).isEqualTo(1);
    }

    @Test
    void shouldFindAllBooksInDatabase() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        BookDTO book2 = new BookDTO("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        bookFacade.addBook(book1);
        bookFacade.addBook(book2);
        //when
        List<BookDTO> bookList = bookFacade.getBooks();
        //then
        assertThat(bookList.size()).isEqualTo(2);
    }

    @Test
    void shouldDeleteBookFromDatabase() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookFacade.addBook(book1);
        //when
        bookFacade.deleteBook(book1.getBookId());
        //then
        final List<BookDTO> books = bookFacade.getBooks();
        assertThat(books).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingBookThatDoesNotExist() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        Throwable thrown = catchThrowable(() ->
                bookFacade.deleteBook(book1.getBookId()));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void shouldFindBookById() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookFacade.addBook(book1);
        //when
        final BookDTO book = bookFacade.findBook(book1.getBookId());
        //than
        assertThat(book).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenFindingBookThatDoesNotExist() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        Throwable thrown = catchThrowable(() ->
                bookFacade.findBook(book1.getBookId()));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void shouldUpdateBook() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        BookDTO book2 = new BookDTO("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        bookFacade.addBook(book1);
        //when
        bookFacade.updateBook(book1.getBookId(), book2);
        //then
        final List<BookDTO> books = bookFacade.getBooks();
        assertThat(books.size()).isEqualTo(1);
        final BookDTO bookFromDB = books.get(0);
        assertThat(bookFromDB.getBookId()).isEqualTo(book1.getBookId());
        assertThat(bookFromDB.getTitle()).isEqualTo(book2.getTitle());
        assertThat(bookFromDB.getAuthor()).isEqualTo(book2.getAuthor());
        assertThat(bookFromDB.getIsbn()).isEqualTo(book2.getIsbn());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingBookThatDoesNotExist() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        BookDTO book2 = new BookDTO("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        //when
        Throwable thrown = catchThrowable(() ->
                bookFacade.updateBook(book1.getBookId(), book2));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenListOfBooksWithGivenTitleAndAuthorIsEmpty() {
        //given
        BookDTO book1 = new BookDTO("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        Throwable thrown = catchThrowable(() ->
                bookFacade.findBooksByTitleAndAuthor(book1.getTitle(), book1.getAuthor()));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }
}
