package com.ticketingSystem.TicketingSimulation.repository;

import com.ticketingSystem.TicketingSimulation.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    }