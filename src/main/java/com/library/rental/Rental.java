package com.library.rental;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    private String rentalId;
    private Instant timeOfRental;
    private String customerId;
    private String bookId;

    Rental() {
    }

    public Rental(String rentalId, Instant timeOfRental, String customerId, String bookId) {
        this.rentalId = rentalId;
        this.timeOfRental = timeOfRental;
        this.customerId = customerId;
        this.bookId = bookId;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
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

    String getCustomerId() {
        return customerId;
    }

    void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    String getBookId() {
        return bookId;
    }

    void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return id == rental.id && Objects.equals(rentalId, rental.rentalId) && Objects.equals(customerId, rental.customerId) && Objects.equals(bookId, rental.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rentalId, customerId, bookId);
    }
}