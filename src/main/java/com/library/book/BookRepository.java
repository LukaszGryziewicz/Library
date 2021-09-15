package com.library.book;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findTopBookByTitleAndAuthorAndRentedIsFalse(String title, String author);

    List<Book> findBooksByTitleAndAuthor(String title, String author);

    Optional<Book> findBookByBookId(String bookId);

    boolean existsByBookId(String bookId);

    boolean existsByTitleAndAuthor(String title, String author);

    @Transactional
    void deleteBookByBookId(String bookId);
}
