package com.library.rental;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

interface RentalRepository extends JpaRepository<Rental, Long> {

    Optional<Rental> findRentalByRentalId(String rentalId);

    List<Rental> findRentalsByCustomerId(String id);

    int countRentalsByCustomerId(String id);

    List<Rental> findRentalsByBookId(String id);

    @Transactional
    void deleteRentalByRentalId(String rentalId);

    boolean existsByRentalId(String rentalId);
}
