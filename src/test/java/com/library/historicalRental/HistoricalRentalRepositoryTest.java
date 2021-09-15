package com.library.historicalRental;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HistoricalRentalRepositoryTest {

    @Autowired
    private HistoricalRentalRepository historicalRentalRepository;

    @Test
    void shouldFindHistoricalRentalsByCustomerId() {
        //given
        HistoricalRental historicalRental = new HistoricalRental();
        historicalRental.setCustomerId("672fca70-67b7-4d5b-b436-e3c51f3d35fd");
        HistoricalRental historicalRental2 = new HistoricalRental();
        historicalRental2.setCustomerId("882a1363-886c-41b8-967a-5b4526bfd62a");
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
        historicalRental.setBookId("672fca70-67b7-4d5b-b436-e3c51f3d35fd");
        HistoricalRental historicalRental2 = new HistoricalRental();
        historicalRental2.setBookId("882a1363-886c-41b8-967a-5b4526bfd62a");
        historicalRentalRepository.saveAll(List.of(historicalRental, historicalRental2));
        //when
        List<HistoricalRental> historicalRentalsByCustomerId = historicalRentalRepository
                .findHistoricalRentalsByBookId(historicalRental.getBookId());
        //then
        assertThat(historicalRentalsByCustomerId).containsExactlyInAnyOrder(historicalRental);
    }
}