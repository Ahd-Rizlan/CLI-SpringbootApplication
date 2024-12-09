package com.ticketingSystem.TicketingSimulation.DTO;

import com.ticketingSystem.TicketingSimulation.entity.Customer;

public class CustomerDTO {

    private String customerId;
    private boolean isVip;
    private int ticketsPerPurchase;
    private int retrievalInterval;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.isVip = customer.isVip();
        this.ticketsPerPurchase = customer.getTicketsPerPurchase();
        this.retrievalInterval = customer.getRetrievalInterval();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public int getTicketsPerPurchase() {
        return ticketsPerPurchase;
    }

    public void setTicketsPerPurchase(int ticketsPerPurchase) {
        this.ticketsPerPurchase = ticketsPerPurchase;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

}
