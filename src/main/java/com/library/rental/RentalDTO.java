package com.library.rental;

import com.library.book.Book;
import com.library.customer.Customer;

import java.util.Objects;

public class RentalDTO {
    private long id;
    private Customer customer;
    private Book book;

    public RentalDTO(Customer customer, Book book) {
        this.customer = customer;
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        RentalDTO rentalDTO = (RentalDTO) o;
        return id == rentalDTO.id && Objects.equals(customer, rentalDTO.customer) && Objects.equals(book, rentalDTO.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, book);
    }
}
