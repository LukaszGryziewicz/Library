package com.library.BookTests;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void shouldAddNewBookToDatabase() {
        //given
        Book book1=new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        bookService.addNewBook(book1);
        //then
        assertThat(bookRepository.findAll()).containsExactlyInAnyOrder(book1);
    }

    @Test
    public void shouldFindAllBooks() {
        //given
        Book book1=new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2=new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");

        bookService.addNewBook(book1);
        bookService.addNewBook(book2);
        //when
        List<Book> bookList = bookService.getBooks();
        //then
        assertThat(bookList).containsExactlyInAnyOrder(book1,book2);
    }

    @Test
    public void shouldDeleteBookFromDatabase() {
        //given
        Book book1=new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");

        bookService.addNewBook(book1);
        //when
        bookService.deleteBook(book1);
        //then
        assertThat(bookRepository.findAll()).isEmpty();
    }
}
