package com.library.rental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    Optional<Rental> findRentalByRentalId(UUID rentalId);

    List<Rental> findRentalsByCustomerId(UUID id);

    int countRentalsByCustomerId(UUID id);

    List<Rental> findRentalByBookId(UUID id);

    void deleteRentalByRentalId(UUID rentalId);

    boolean existsByRentalId(UUID rentalId);

}
