package com.ticketingSystem.TicketingSimulation.exceptionHandling;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String>handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return  new ResponseEntity<>(entityNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
    }
}
