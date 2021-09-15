package com.library.rental;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RentalRepositoryTest {

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    void shouldFindRentalByRentalId() {
        //given
        Rental rental = new Rental();
        rental.setRentalId(randomUUID().toString());
        rentalRepository.save(rental);
        //when
        Optional<Rental> rentalByRentalId = rentalRepository
                .findRentalByRentalId(rental.getRentalId());
        //then
        assertThat(rentalByRentalId).isPresent();
    }

    @Test
    void shouldNotFindRentalByRentalId() {
        //given
        String id = randomUUID().toString();
        //when
        Optional<Rental> rentalByRentalId = rentalRepository
                .findRentalByRentalId(id);
        //then
        assertThat(rentalByRentalId).isNotPresent();
    }

    @Test
    void shouldReturnTrueWhenRentalExists() {
        //given
        Rental rental = new Rental();
        rental.setRentalId(randomUUID().toString());
        rentalRepository.save(rental);
        //when
        boolean existsByRentalId = rentalRepository
                .existsByRentalId(rental.getRentalId());
        //then
        assertThat(existsByRentalId).isTrue();
    }

    @Test
    void shouldReturnFalseWhenRentalDoesNotExist() {
        //given
        String id = randomUUID().toString();
        //when
        boolean existsByRentalId = rentalRepository
                .existsByRentalId(id);
        //then
        assertThat(existsByRentalId).isFalse();
    }

    @Test
    void shouldFindRentalsByCustomerId() {
        //given
        Rental rental = new Rental();
        rental.setCustomerId(randomUUID().toString());
        Rental rental2 = new Rental();
        rentalRepository.saveAll(List.of(rental, rental2));
        //when
        List<Rental> rentalsByCustomerId = rentalRepository
                .findRentalsByCustomerId(rental.getCustomerId());
        //then
        assertThat(rentalsByCustomerId).containsExactlyInAnyOrder(rental);
    }

    @Test
    void shouldCountRentalsByCustomerId() {
        //given
        Rental rental = new Rental();
        rental.setCustomerId(randomUUID().toString());
        Rental rental2 = new Rental();
        rentalRepository.saveAll(List.of(rental, rental2));
        //when
        int numberOfCustomerRentals = rentalRepository
                .countRentalsByCustomerId(rental.getCustomerId());
        //then
        assertThat(numberOfCustomerRentals).isEqualTo(1);

    }

    @Test
    void shouldFindRentalsByBookId() {
        //given
        Rental rental = new Rental();
        rental.setBookId(randomUUID().toString());
        Rental rental2 = new Rental();
        rentalRepository.saveAll(List.of(rental, rental2));
        //when
        List<Rental> rentalsByBookId = rentalRepository
                .findRentalsByBookId(rental.getBookId());
        //then
        assertThat(rentalsByBookId).containsExactlyInAnyOrder(rental);
    }

    @Test
    void shouldDeleteRentalByRentalId() {
        //given
        Rental rental = new Rental();
        rental.setRentalId(randomUUID().toString());
        rentalRepository.save(rental);
        //when
        rentalRepository.deleteRentalByRentalId(rental.getRentalId());
        //then
        List<Rental> rentals = rentalRepository.findAll();
        assertThat(rentals).isEmpty();
    }
}