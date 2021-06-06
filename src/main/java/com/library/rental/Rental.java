package com.library.rental;

import com.library.book.Book;
import com.library.customer.Customer;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Customer customer;

    public Rental() {
    }

}
