package com.library.historicalRental;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HistoricalRentalRepositoryTest {

    @Autowired
    private HistoricalRentalRepository historicalRentalRepository;

    @Test
    void shouldFindHistoricalRentalsByCustomerId() {
        //given
        HistoricalRental historicalRental = new HistoricalRental();
        historicalRental.setCustomerId(randomUUID().toString());
        HistoricalRental historicalRental2 = new HistoricalRental();
        historicalRental2.setCustomerId(randomUUID().toString());
        historicalRentalRepository.saveAll(List.of(historicalRental, historicalRental2));
        //when
        List<HistoricalRental> historicalRentalsByCustomerId = historicalRentalRepository
                .findHistoricalRentalsByCustomerId(historicalRental.getCustomerId());
        //then
        assertThat(historicalRentalsByCustomerId).containsExactlyInAnyOrder(historicalRental);

    }

    @Test
    void shouldFindHistoricalRentalsByBookId() {
        //given
        HistoricalRental historicalRental = new HistoricalRental();
        historicalRental.setBookId(randomUUID().toString());
        HistoricalRental historicalRental2 = new HistoricalRental();
        historicalRental2.setBookId(randomUUID().toString());
        historicalRentalRepository.saveAll(List.of(historicalRental, historicalRental2));
        //when
        List<HistoricalRental> historicalRentalsByCustomerId = historicalRentalRepository
                .findHistoricalRentalsByBookId(historicalRental.getBookId());
        //then
        assertThat(historicalRentalsByCustomerId).containsExactlyInAnyOrder(historicalRental);
    }
}