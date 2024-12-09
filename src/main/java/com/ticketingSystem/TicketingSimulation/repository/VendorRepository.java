package com.ticketingSystem.TicketingSimulation.repository;

import com.ticketingSystem.TicketingSimulation.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, String> {
}
