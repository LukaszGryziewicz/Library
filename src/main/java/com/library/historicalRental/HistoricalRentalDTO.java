package com.library.historicalRental;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class HistoricalRentalDTO {
    private String historicalRentalId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateEnded;
    private String customerId;
    private String firstName;
    private String lastName;
    private String bookId;
    private String title;
    private String author;
    private String isbn;

    public HistoricalRentalDTO() {
    }

    public HistoricalRentalDTO(
            String historicalRentalId,
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

    public String getHistoricalRentalId() {
        return historicalRentalId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateEnded() {
        return dateEnded;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalRentalDTO that = (HistoricalRentalDTO) o;
        return Objects.equals(historicalRentalId, that.historicalRentalId) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateEnded, that.dateEnded) && Objects.equals(customerId, that.customerId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(bookId, that.bookId) && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historicalRentalId, dateCreated, dateEnded, customerId, firstName, lastName, bookId, title, author, isbn);
    }
}
