package com.ticketingSystem.TicketingSimulation.dto;

import com.ticketingSystem.TicketingSimulation.model.Configuration;

public class ConfigurationDTO {
    private int totalTickets;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public ConfigurationDTO(Configuration configuration) {
        this.totalTickets= configuration.getTotalTickets();
        this.maxTicketCapacity= configuration.getMaxTicketCapacity();
        this.ticketReleaseRate= configuration.getTicketReleaseRate();
        this.maxTicketCapacity = configuration.getMaxTicketCapacity();
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
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
