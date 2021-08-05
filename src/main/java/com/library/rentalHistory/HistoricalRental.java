package com.library.rentalHistory;

import javax.persistence.*;
import java.time.Instant;

@Entity
class HistoricalRental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    private String historicalRentalId;
    private Instant dateCreated;
    private Instant dateEnded;
    //book
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    //customer
    private String customerId;
    private String firstName;
    private String lastName;

    HistoricalRental() {
    }

    HistoricalRental(String historicalRentalId, Instant dateCreated, Instant dateEnded, String bookId, String title, String author, String isbn, String customerId, String firstName, String lastName) {
        this.historicalRentalId = historicalRentalId;
        this.dateCreated = dateCreated;
        this.dateEnded = dateEnded;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    long getId() {
        return id;
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

    String getCustomerId() {
        return customerId;
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
}

