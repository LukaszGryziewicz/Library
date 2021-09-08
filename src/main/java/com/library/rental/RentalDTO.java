package com.library.rental;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class RentalDTO {
    private String rentalId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeOfRental;
    private String customerId;
    private String firstName;
    private String lastName;
    private String bookId;
    private String title;
    private String author;
    private String isbn;

    public RentalDTO() {
    }

    public RentalDTO(String rentalId, LocalDateTime timeOfRental, String customerId, String firstName, String lastName, String bookId, String title, String author, String isbn) {
        this.rentalId = rentalId;
        this.timeOfRental = timeOfRental;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public LocalDateTime getTimeOfRental() {
        return timeOfRental;
    }

    public void setTimeOfRental(LocalDateTime timeOfRental) {
        this.timeOfRental = timeOfRental;
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
        RentalDTO rentalDTO = (RentalDTO) o;
        return Objects.equals(rentalId, rentalDTO.rentalId) && Objects.equals(timeOfRental, rentalDTO.timeOfRental) && Objects.equals(customerId, rentalDTO.customerId) && Objects.equals(firstName, rentalDTO.firstName) && Objects.equals(lastName, rentalDTO.lastName) && Objects.equals(bookId, rentalDTO.bookId) && Objects.equals(title, rentalDTO.title) && Objects.equals(author, rentalDTO.author) && Objects.equals(isbn, rentalDTO.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, timeOfRental, customerId, firstName, lastName, bookId, title, author, isbn);
    }
}
