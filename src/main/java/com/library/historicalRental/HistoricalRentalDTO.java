package com.library.historicalRental;

import java.time.Instant;

public class HistoricalRentalDTO {
    private final String historicalRentalId;
    private final String isbn;
    private final String customerId;
    private Instant dateCreated;
    private Instant dateEnded;
    private String bookId;
    private String title;
    private String author;
    private String firstName;
    private String lastName;

    public HistoricalRentalDTO(String historicalRentalId, Instant dateCreated, Instant dateEnded, String bookId, String title, String author, String isbn, String customerId, String firstName, String lastName) {
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

    public String getHistoricalRentalId() {
        return historicalRentalId;
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

    public String getCustomerId() {
        return customerId;
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
}
