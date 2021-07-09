package com.library.rental;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;

    private UUID rentalId;
    private UUID customerId;
    private UUID bookId;

    public Rental() {
    }

    public Rental(UUID rentalId, UUID customerId, UUID bookId) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.bookId = bookId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getRentalId() {
        return rentalId;
    }

    public void setRentalId(UUID rentalId) {
        this.rentalId = rentalId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
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