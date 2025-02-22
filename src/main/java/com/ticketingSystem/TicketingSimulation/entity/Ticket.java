package com.ticketingSystem.TicketingSimulation.entity;


import com.ticketingSystem.TicketingSimulation.constant.TicketStatus;
import com.ticketingSystem.TicketingSimulation.validation.AutoIdGeneration;

public class Ticket {
    private static final AutoIdGeneration ticketAutoIdGeneration = new AutoIdGeneration(0);

    private String ticketId;
    private TicketStatus status;
    private Vendor vendor;


    public Ticket(Vendor Vendor) {
        this.ticketId = ticketAutoIdGeneration.generateAutoId("TId");
        this.status = TicketStatus.PENDING;
        this.vendor = Vendor;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getTicketId() {
        return ticketId;
    }


    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket [ticketId=" + ticketId + "]";
    }
}


