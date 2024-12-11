package com.ticketingSystem.TicketingSimulation.entity;

public class VipCustomer extends Customer{
    private final boolean isVip;

    public VipCustomer(int ticketsPerPurchase, Ticketpool ticketPool, Configuration config) {

        super(ticketsPerPurchase, ticketPool, config);
        this.isVip = true;
        super.setCustomerId("V" + super.getCustomerId());
    }
}
