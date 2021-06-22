package com.library.rental;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.book.Book;
import com.library.customer.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Book book;

    private boolean returned;

    public Rental() {
    }

    public Rental(Customer customer, Book book) {
        this.customer = customer;
        this.book = book;
    }

    public Rental(Customer customer, Book book, boolean returned) {
        this.customer = customer;
        this.book = book;
        this.returned = returned;
    }

    public long getId() {
        return id;
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

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

}