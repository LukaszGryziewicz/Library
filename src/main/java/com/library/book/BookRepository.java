package com.library.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findTopBookByTitleAndAuthorAndRentedIsFalse(String title, String author);

    List<Book> findBooksByTitleAndAuthor(String title, String author);

    Optional<Book> findBookByBookId(UUID bookId);

    boolean existsByBookId(UUID bookId);

    void deleteBookByBookId(UUID bookId);
}
