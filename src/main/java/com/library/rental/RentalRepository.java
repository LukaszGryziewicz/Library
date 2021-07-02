package com.library.rental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    Optional<Rental> findRentalById(Long id);

    List<Rental> findRentalsByCustomerId(Long id);

    int countRentalsByCustomerId(Long id);

    List<Rental> findRentalByBookId(Long id);

}
