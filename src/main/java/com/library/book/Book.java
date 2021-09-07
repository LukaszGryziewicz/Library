package com.library.book;

import javax.persistence.*;
import java.util.Objects;

@Entity
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private boolean rented;

    Book() {
    }

    Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    String getBookId() {
        return bookId;
    }

    void setBookId(String bookId) {
        this.bookId = bookId;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getAuthor() {
        return author;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    String getIsbn() {
        return isbn;
    }

    void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    boolean isRented() {
        return rented;
    }

    void setRented(boolean rented) {
        this.rented = rented;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && rented == book.rented && Objects.equals(bookId, book.bookId) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, title, author, isbn, rented);
    }

    void rent() {
        rented = true;
    }

    void returnBook() {
        rented = false;
    }

    void update(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }
}
