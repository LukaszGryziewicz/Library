package com.library.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    Optional<Rental> findRentalById(Long id);

    @Query("SELECT r FROM Rental r WHERE r.returned=false")
    List<Rental> findUnfinishedRentals();

    @Query("SELECT r FROM Rental r WHERE r.returned=true")
    List<Rental> findFinishedRentals();

    List<Rental> findRentalsByCustomerId(Long id);

    List<Rental> findRentalByBookId(Long id);

}
