package com.library.historicalRental;

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

    public HistoricalRental(String historicalRentalId, Instant dateCreated, Instant dateEnded, String customerId, String firstName, String lastName, String bookId, String title, String author, String isbn) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHistoricalRentalId() {
        return historicalRentalId;
    }

    public void setHistoricalRentalId(String historicalRentalId) {
        this.historicalRentalId = historicalRentalId;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(Instant dateEnded) {
        this.dateEnded = dateEnded;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

