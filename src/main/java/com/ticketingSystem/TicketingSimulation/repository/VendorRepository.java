package com.ticketingSystem.TicketingSimulation.repository;

import com.ticketingSystem.TicketingSimulation.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, String> {
}
