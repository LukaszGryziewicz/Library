package com.library.rentalHistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricalRentalRepository extends JpaRepository<HistoricalRental, Long> {

    List<HistoricalRental> findHistoricalRentalsByFirstNameAndLastName(String firstName, String lastName);

    List<HistoricalRental> findHistoricalRentalsByTitleAndAuthor(String title, String author);

    List<HistoricalRental> findHistoricalRentalsByFirstNameAndLastNameAndTitleAndAuthor(String firstName, String lastName, String title, String author);

}
