package com.library.customer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    private String customerId;
    private String firstName;
    private String lastName;
    @ElementCollection
    private List<String> fines = new ArrayList<>();

    Customer() {
    }

    Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    List<String> getFines() {
        return fines;
    }

    void setFines(List<String> fines) {
        this.fines = fines;
    }

    void update(Customer customer) {
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
    }

    void addFine(String fine) {
        fines.add(fine);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(customerId, customer.customerId) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, firstName, lastName);
    }
}
