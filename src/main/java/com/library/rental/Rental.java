package com.library.rental;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
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

    LocalDateTime getTimeOfRental() {
        return timeOfRental;
    }

    String getCustomerId() {
        return customerId;
    }

    String getBookId() {
        return bookId;
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