package com.library.rental;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String rentalId;
    private LocalDateTime timeOfRental;
    private String customerId;
    private String bookId;

    protected Rental() {
    }

    Rental(String rentalId, LocalDateTime timeOfRental, String customerId, String bookId) {
        this.rentalId = rentalId;
        this.timeOfRental = timeOfRental;
        this.customerId = customerId;
        this.bookId = bookId;
    }

    String getRentalId() {
        return rentalId;
    }

    void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    LocalDateTime getTimeOfRental() {
        return timeOfRental;
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
        return Objects.equals(id, rental.id) && Objects.equals(rentalId, rental.rentalId) && Objects.equals(customerId, rental.customerId) && Objects.equals(bookId, rental.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rentalId, customerId, bookId);
    }
}