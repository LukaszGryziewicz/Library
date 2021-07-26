package com.library.rental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    Optional<Rental> findRentalByRentalId(UUID rentalId);

    List<Rental> findRentalsByCustomerId(String id);

    int countRentalsByCustomerId(String id);

    List<Rental> findRentalByBookId(String id);

    void deleteRentalByRentalId(UUID rentalId);

    boolean existsByRentalId(UUID rentalId);

}
