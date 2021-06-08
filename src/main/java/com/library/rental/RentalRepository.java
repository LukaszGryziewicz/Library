package com.library.rental;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT r FROM Rental r WHERE r.returned=false")
    List<Rental> findUnfinishedRentals();

    @Query("SELECT r FROM Rental r WHERE r.returned=true")
    List<Rental> findFinishedRentals();


}
