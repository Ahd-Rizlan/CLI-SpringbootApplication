package com.ticketingSystem.TicketingSimulation.validation;

import java.util.concurrent.atomic.AtomicInteger;

public class AutoIdGeneration {

    private static AtomicInteger incrementalValue = new AtomicInteger(0); // Atomic Integer for thread-safe increments

    // Constructor
    public AutoIdGeneration(int startingValue) {
        this.incrementalValue = new AtomicInteger(startingValue); // Start from 0 to achieve ID starting from 1
    }


    // Generates a unique ID with the specified prefix
    public String generateAutoId(String prefix) {
        int id = incrementalValue.incrementAndGet(); // Atomically increment the value
        return prefix + id;
    }
}
