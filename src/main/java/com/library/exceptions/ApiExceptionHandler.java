package com.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {CustomerNotFoundException.class,BookNotFoundException.class,RentalNotFoundException.class})
    ResponseEntity<Object> handleNotFoundExceptions(Exception exception) {
        ApiException apiException = new ApiException(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoBookAvailableException.class)
    ResponseEntity<Object>handleNoBookAvailableException(NoBookAvailableException exception){
        ApiException apiException= new ApiException(exception.getMessage(),HttpStatus.NOT_FOUND,LocalDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RentalAlreadyFinishedException.class)
    ResponseEntity<Object>handleRentalAlreadyFinishedException(RentalNotFoundException exception){
        ApiException apiException = new ApiException(exception.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExceededMaximumNumberOfRentalsException.class)
    ResponseEntity<Object>handleExceededMaximumNumberOfRentalsException(ExceededMaximumNumberOfRentalsException exception){
        ApiException apiException = new ApiException(exception.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());

        return new ResponseEntity<>(apiException,HttpStatus.CONFLICT);
    }


}
