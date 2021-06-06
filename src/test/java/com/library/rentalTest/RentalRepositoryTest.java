package com.library.rentalTest;

import com.library.book.Book;
import com.library.rental.Rental;
import com.library.rental.RentalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class RentalRepositoryTest {
    @Autowired
    private RentalRepository rentalRepository;

    @Test
    public void should() {
        //given
        rentalRepository.save(new Rental());

        //when

        //then
    }
}
