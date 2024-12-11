package com.ticketingSystem.TicketingSimulation.entity;


public class RegularCustomer extends Customer{ 
    private final boolean isVip;

    public RegularCustomer(int ticketsPerPurchase, Ticketpool ticketPool, Configuration config) {
        super(ticketsPerPurchase, ticketPool, config);
        this.isVip = false;

    }
}
