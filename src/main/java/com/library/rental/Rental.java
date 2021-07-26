package com.library.rental;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;

    private UUID rentalId;
    private UUID customerId;
    private String bookId;

    Rental() {
    }

    Rental(UUID rentalId, UUID customerId, String bookId) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.bookId = bookId;
    }


    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    UUID getRentalId() {
        return rentalId;
    }

    void setRentalId(UUID rentalId) {
        this.rentalId = rentalId;
    }

    UUID getCustomerId() {
        return customerId;
    }

    void setCustomerId(UUID customerId) {
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