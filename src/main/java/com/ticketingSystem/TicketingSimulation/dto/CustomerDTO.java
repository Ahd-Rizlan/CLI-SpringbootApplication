package com.ticketingSystem.TicketingSimulation.dto;

public class CustomerDTO {
    private String customerId;
   private boolean isVip;
    private int ticketsPerPurchase;
    private int retrievalInterval;

    public CustomerDTO(String customerId, boolean isVip, int ticketsPerPurchase, int retrievalInterval) {
        this.customerId = customerId;
        this.isVip = isVip;
        this.ticketsPerPurchase = ticketsPerPurchase;
        this.retrievalInterval = retrievalInterval;
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
