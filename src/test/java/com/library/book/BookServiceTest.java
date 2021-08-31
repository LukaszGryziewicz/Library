package com.library.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
public class BookServiceTest {

    @Autowired
    BookFacade bookFacade;

    private BookDTO createBook(String title, String author, String isbn) {
        BookDTO book = new BookDTO(title, author, isbn);
        bookFacade.addBook(book);
        return book;
    }

    @Test
    void shouldAddBook() {
        //when
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //then
        BookDTO bookFromDB = bookFacade.findBook(book.getBookId());
        assertThat(bookFromDB).isEqualTo(book);
    }

    @Test
    void shouldFindAllBooks() {
        //given
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = createBook("The Odyssey", "Homer", "987654321");
        //when
        List<BookDTO> bookList = bookFacade.getBooks();
        //then
        assertThat(bookList).containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    void shouldDeleteBook() {
        //given
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //when
        bookFacade.deleteBook(book.getBookId());
        //then
        final List<BookDTO> bookList = bookFacade.getBooks();
        assertThat(bookList).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingBookThatDoesNotExist() {
        //given
        String randomId = randomUUID().toString();
        //when
        Throwable thrown = catchThrowable(()
                -> bookFacade.deleteBook(randomId));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void shouldFindBookById() {
        //given
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //when
        final BookDTO bookFromDB = bookFacade.findBook(book.getBookId());
        //then
        assertThat(bookFromDB).isEqualTo(book);
    }

    @Test
    void shouldThrowExceptionWhenFindingBookThatDoesNotExist() {
        //given
        String randomId = randomUUID().toString();
        //when
        Throwable thrown = catchThrowable(() -> bookFacade.findBook(randomId));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldUpdateBook() {
        //given
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = new BookDTO("The Odyssey", "Homer", "987654321");
        //when
        bookFacade.updateBook(book1.getBookId(), book2);
        //then
        final List<BookDTO> bookList = bookFacade.getBooks();
        assertThat(bookList.size()).isEqualTo(1);
        final BookDTO bookFromDB = bookList.get(0);
        assertThat(bookFromDB.getBookId()).isEqualTo(book1.getBookId());
        assertThat(bookFromDB.getTitle()).isEqualTo(book2.getTitle());
        assertThat(bookFromDB.getAuthor()).isEqualTo(book2.getAuthor());
        assertThat(bookFromDB.getIsbn()).isEqualTo(book2.getIsbn());
        assertThat(bookFromDB.isRented()).isEqualTo(book2.isRented());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingBookThatDoesNotExist() {
        //given
        String randomId = randomUUID().toString();
        BookDTO book = new BookDTO("The Odyssey", "Homer", "987654321");
        //when
        Throwable thrown = catchThrowable(() ->
                bookFacade.updateBook(randomId, book));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenListOfBooksWithGivenTitleAndAuthorIsEmpty() {
        //given
        String randomString = randomUUID().toString();
        //when
        Throwable thrown = catchThrowable(() ->
                bookFacade.findBooksByTitleAndAuthor(randomString, randomString));
        //then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldFindFirstAvailableBook() {
        //given
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = createBook("Hamlet", "William Shakespeare", "123456789");
        bookFacade.rentBook(book1.getBookId());
        //when
        BookDTO firstAvailableBook = bookFacade
                .findFirstAvailableBookByTitleAndAuthor(book1.getTitle(), book1.getAuthor());
        //then
        assertThat(firstAvailableBook).isEqualTo(book2);
    }
}
