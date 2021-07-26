package com.library.rental;

public class RentalDTO {
    private String rentalId;
    private String customerId;
    private String bookId;

    public RentalDTO() {
    }

    public RentalDTO(String rentalId, String customerId, String bookId) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.bookId = bookId;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
