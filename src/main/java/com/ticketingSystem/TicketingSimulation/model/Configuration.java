package com.ticketingSystem.TicketingSimulation.model;


import java.io.Serial;
import java.io.Serializable;

public class Configuration implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int totalTickets;//Number of tickets released at once
    private int maxTicketCapacity;//max a pool can hold at a moment
    private int ticketReleaseRate;
    private int customerRetrievalRate;



    public Configuration() {}

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

}