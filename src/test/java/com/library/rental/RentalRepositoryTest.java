package com.library.rental;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RentalRepositoryTest {

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    void shouldFindRentalByRentalId() {
        //given
        Rental rental = new Rental(
                "524b09c8-c2ba-434e-9d21-f7e52b9370c4",
                now(),
                "80e56b65-938b-46fa-8aea-9a31517acf26",
                "bf84d1b4-1ff0-450c-bc0f-c08ddccdc4c1"
        );
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
        String id = "524b09c8-c2ba-434e-9d21-f7e52b9370c4";
        //when
        Optional<Rental> rentalByRentalId = rentalRepository
                .findRentalByRentalId(id);
        //then
        assertThat(rentalByRentalId).isNotPresent();
    }

    @Test
    void shouldReturnTrueWhenRentalExists() {
        //given
        Rental rental = new Rental(
                "524b09c8-c2ba-434e-9d21-f7e52b9370c4",
                now(),
                "80e56b65-938b-46fa-8aea-9a31517acf26",
                "bf84d1b4-1ff0-450c-bc0f-c08ddccdc4c1"
        );
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
        String id = "524b09c8-c2ba-434e-9d21-f7e52b9370c4";
        //when
        boolean existsByRentalId = rentalRepository
                .existsByRentalId(id);
        //then
        assertThat(existsByRentalId).isFalse();
    }

    @Test
    void shouldFindRentalsByCustomerId() {
        //given
        Rental rental = new Rental(
                "524b09c8-c2ba-434e-9d21-f7e52b9370c4",
                now(),
                "80e56b65-938b-46fa-8aea-9a31517acf26",
                "bf84d1b4-1ff0-450c-bc0f-c08ddccdc4c1"
        );
        Rental rental2 = new Rental(
                "392b10ce-df7f-4a5c-a1a5-55587bda1e57",
                now(),
                "9368e414-620a-4c9e-973a-923980795dc0",
                "bf84d1b4-1ff0-450c-bc0f-c08ddccdc4c1"
        );
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
        Rental rental = new Rental(
                "524b09c8-c2ba-434e-9d21-f7e52b9370c4",
                now(),
                "80e56b65-938b-46fa-8aea-9a31517acf26",
                "bf84d1b4-1ff0-450c-bc0f-c08ddccdc4c1"
        );
        Rental rental2 = new Rental(
                "392b10ce-df7f-4a5c-a1a5-55587bda1e57",
                now(),
                "9368e414-620a-4c9e-973a-923980795dc0",
                "bf84d1b4-1ff0-450c-bc0f-c08ddccdc4c1"
        );
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
        Rental rental = new Rental(
                "524b09c8-c2ba-434e-9d21-f7e52b9370c4",
                now(),
                "80e56b65-938b-46fa-8aea-9a31517acf26",
                "bf84d1b4-1ff0-450c-bc0f-c08ddccdc4c1"
        );
        Rental rental2 = new Rental(
                "392b10ce-df7f-4a5c-a1a5-55587bda1e57",
                now(),
                "80e56b65-938b-46fa-8aea-9a31517acf26",
                "d6b5a91c-0b8c-404d-bc0d-15546a92bb44"
        );
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
        Rental rental = new Rental(
                "524b09c8-c2ba-434e-9d21-f7e52b9370c4",
                now(),
                "80e56b65-938b-46fa-8aea-9a31517acf26",
                "bf84d1b4-1ff0-450c-bc0f-c08ddccdc4c1"
        );
        rentalRepository.save(rental);
        //when
        rentalRepository.deleteRentalByRentalId(rental.getRentalId());
        //then
        List<Rental> rentals = rentalRepository.findAll();
        assertThat(rentals).isEmpty();
    }
}