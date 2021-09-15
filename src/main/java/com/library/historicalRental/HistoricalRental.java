package com.library.historicalRental;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
class HistoricalRental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String historicalRentalId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateEnded;
    //customer
    private String customerId;
    private String firstName;
    private String lastName;
    //book
    private String bookId;
    private String title;
    private String author;
    private String isbn;

    protected HistoricalRental() {
    }

    HistoricalRental(String historicalRentalId,
                     LocalDateTime dateCreated,
                     LocalDateTime dateEnded,
                     String customerId,
                     String firstName,
                     String lastName,
                     String bookId,
                     String title,
                     String author,
                     String isbn
    ) {
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

    String getHistoricalRentalId() {
        return historicalRentalId;
    }

    LocalDateTime getDateCreated() {
        return dateCreated;
    }

    LocalDateTime getDateEnded() {
        return dateEnded;
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

    String getLastName() {
        return lastName;
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

    String getAuthor() {
        return author;
    }

    String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalRental that = (HistoricalRental) o;
        return Objects.equals(id, that.id) && Objects.equals(historicalRentalId, that.historicalRentalId) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateEnded, that.dateEnded) && Objects.equals(customerId, that.customerId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(bookId, that.bookId) && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, historicalRentalId, dateCreated, dateEnded, customerId, firstName, lastName, bookId, title, author, isbn);
    }
}

