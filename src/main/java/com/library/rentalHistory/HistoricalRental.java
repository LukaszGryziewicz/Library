package com.library.rentalHistory;

import javax.persistence.*;

@Entity
public class HistoricalRental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    //book
    private String title;
    private String author;
    private String isbn;
    //customer
    private String firstName;
    private String lastName;

    public HistoricalRental() {
    }

    public HistoricalRental(String title, String author, String isbn, String firstName, String lastName) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

