package com.library.rental;

import com.library.book.Book;
import com.library.customer.Customer;

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

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Book book;

    public Rental() {
    }

    public Rental(Customer customer, Book book) {
        this.customer = customer;
        this.book = book;
    }

    public Rental(UUID rentalId, Customer customer, Book book) {
    }

    public long getId() {
        return id;
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
        Rental rental = (Rental) o;
        return id == rental.id && Objects.equals(customer, rental.customer) && Objects.equals(book, rental.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, book);
    }
}