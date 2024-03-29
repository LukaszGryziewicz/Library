package com.library.exceptions;

import com.library.book.BookNotFoundException;
import com.library.book.NoBookAvailableException;
import com.library.customer.CustomerNotFoundException;
import com.library.rental.ExceededMaximumNumberOfRentalsException;
import com.library.rental.RentalNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {CustomerNotFoundException.class, BookNotFoundException.class, RentalNotFoundException.class})
    ResponseEntity<Object> handleNotFoundExceptions(Exception e) {
        ApiException apiException = new ApiException(e.getMessage(), BAD_REQUEST, now());
        return new ResponseEntity<>(apiException, BAD_REQUEST);
    }

    @ExceptionHandler(NoBookAvailableException.class)
    ResponseEntity<Object> handleNoBookAvailableException(NoBookAvailableException e) {
        ApiException apiException = new ApiException(e.getMessage(), BAD_REQUEST, now());
        return new ResponseEntity<>(apiException, BAD_REQUEST);
    }

    @ExceptionHandler(ExceededMaximumNumberOfRentalsException.class)
    ResponseEntity<Object> handleExceededMaximumNumberOfRentalsException(ExceededMaximumNumberOfRentalsException e) {
        ApiException apiException = new ApiException(e.getMessage(), CONFLICT, now());
        return new ResponseEntity<>(apiException, CONFLICT);
    }


}
