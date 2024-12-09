package com.ticketingSystem.TicketingSimulation.DTO;


import com.ticketingSystem.TicketingSimulation.entity.Configuration;

public class ConfigurationDTO {

    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public ConfigurationDTO() {
    }

    public ConfigurationDTO(Configuration configuration) {

        this.maxTicketCapacity = configuration.getMaxTicketCapacity();
        this.totalTickets = configuration.getTotalTickets();
        this.ticketReleaseRate = configuration.getTicketReleaseRate();
        this.customerRetrievalRate = configuration.getCustomerRetrievalRate();
    }



    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

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

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }
}

