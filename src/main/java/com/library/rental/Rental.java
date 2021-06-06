package com.library.rental;

import com.library.book.Book;
import com.library.customer.Customer;
import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Book book;

    private boolean returned;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime timeOfRental;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime timeOfReturn;

    public Rental() {
    }

    public Rental(Customer customer, Book book, boolean returned, LocalDateTime timeOfRental, LocalDateTime timeOfReturn) {
        this.customer = customer;
        this.book = book;
        this.returned = returned;
        this.timeOfRental = timeOfRental;
        this.timeOfReturn = timeOfReturn;
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
