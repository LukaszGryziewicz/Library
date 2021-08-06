package com.library.historicalRental;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricalRentalRepository extends JpaRepository<HistoricalRental, Long> {

    List<HistoricalRental> findHistoricalRentalsByCustomerId(String customerId);

    List<HistoricalRental> findHistoricalRentalsByBookId(String bookId);

}
