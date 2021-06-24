package com.library.rentalHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalRentalRepository extends JpaRepository<HistoricalRental,Long> {

}
