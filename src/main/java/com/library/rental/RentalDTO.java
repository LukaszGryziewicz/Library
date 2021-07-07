package com.library.rental;

import com.library.book.Book;
import com.library.customer.Customer;

import java.util.Objects;
import java.util.UUID;

public class RentalDTO {
    private UUID rentalId;
    private Customer customer;
    private Book book;

    public RentalDTO(UUID rentalId, Customer customer, Book book) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.book = book;
    }

    public UUID getRentalId() {
        return rentalId;
    }

    public void setRentalId(UUID rentalId) {
        this.rentalId = rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDTO rentalDTO = (RentalDTO) o;
        return Objects.equals(rentalId, rentalDTO.rentalId) && Objects.equals(customer, rentalDTO.customer) && Objects.equals(book, rentalDTO.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, customer, book);
    }
}
