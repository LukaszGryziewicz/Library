package com.library.rental;

import java.time.Instant;

public class RentalDTO {
    private String rentalId;
    private Instant timeOfRental;
    private String customerId;
    private String firstName;
    private String lastName;
    private String bookId;
    private String title;
    private String author;
    private String isbn;

    public RentalDTO() {
    }

    public RentalDTO(String rentalId, Instant timeOfRental, String customerId, String firstName, String lastName, String bookId, String title, String author, String isbn) {
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

    public Instant getTimeOfRental() {
        return timeOfRental;
    }

    public void setTimeOfRental(Instant timeOfRental) {
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
}
