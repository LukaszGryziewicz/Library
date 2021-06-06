package com.library.BookTests;

import com.library.book.Book;
import com.library.book.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void shouldFindBookByTitleAndAuthor() {
        //given
        Book book1 = bookRepository.save(new Book("Adam z Nikiszowca", "Adam Dominik", "123456789"));
        //when
        Optional<Book> bookByTitleAndAuthor = bookRepository.findBookByTitleAndAuthor(book1.getTitle(), book1.getAuthor());
        //then
        assertThat(bookByTitleAndAuthor.isPresent()).isTrue();
    }
}
