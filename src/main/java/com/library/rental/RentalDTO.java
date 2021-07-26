package com.library.rental;

import java.util.UUID;

public class RentalDTO {
    private UUID rentalId;
    private UUID customerId;
    private String bookId;

    public RentalDTO() {
    }

    public RentalDTO(UUID rentalId, UUID customerId, String bookId) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.bookId = bookId;
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
