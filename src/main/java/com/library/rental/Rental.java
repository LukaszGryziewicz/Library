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

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeOfRental;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeOfReturn;

    public Rental() {
    }

    public Rental(Customer customer, Book book) {
        this.customer = customer;
        this.book = book;
    }

    public Rental(Customer customer, Book book, boolean returned, LocalDateTime timeOfRental, LocalDateTime timeOfReturn) {
        this.customer = customer;
        this.book = book;
        this.returned = returned;
        this.timeOfRental = timeOfRental;
        this.timeOfReturn = timeOfReturn;
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

    public LocalDateTime getTimeOfRental() {
        return timeOfRental;
    }

    public void setTimeOfRental(LocalDateTime timeOfRental) {
        this.timeOfRental = timeOfRental;
    }

    public LocalDateTime getTimeOfReturn() {
        return timeOfReturn;
    }

    public void setTimeOfReturn(LocalDateTime timeOfReturn) {
        this.timeOfReturn = timeOfReturn;
    }
}
