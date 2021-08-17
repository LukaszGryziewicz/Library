package com.library.historicalRental;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
class HistoricalRental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    private String historicalRentalId;
    private Instant dateCreated;
    private Instant dateEnded;
    //customer
    private String customerId;
    private String firstName;
    private String lastName;
    //book
    private String bookId;
    private String title;
    private String author;
    private String isbn;

    HistoricalRental() {
    }

    HistoricalRental(String historicalRentalId, Instant dateCreated, Instant dateEnded, String customerId, String firstName, String lastName, String bookId, String title, String author, String isbn) {
        this.historicalRentalId = historicalRentalId;
        this.dateCreated = dateCreated;
        this.dateEnded = dateEnded;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    String getHistoricalRentalId() {
        return historicalRentalId;
    }

    void setHistoricalRentalId(String historicalRentalId) {
        this.historicalRentalId = historicalRentalId;
    }

    Instant getDateCreated() {
        return dateCreated;
    }

    void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    Instant getDateEnded() {
        return dateEnded;
    }

    void setDateEnded(Instant dateEnded) {
        this.dateEnded = dateEnded;
    }

    String getCustomerId() {
        return customerId;
    }

    void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getBookId() {
        return bookId;
    }

    void setBookId(String bookId) {
        this.bookId = bookId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalRental that = (HistoricalRental) o;
        return id == that.id && Objects.equals(historicalRentalId, that.historicalRentalId) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateEnded, that.dateEnded) && Objects.equals(customerId, that.customerId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(bookId, that.bookId) && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, historicalRentalId, dateCreated, dateEnded, customerId, firstName, lastName, bookId, title, author, isbn);
    }
}

