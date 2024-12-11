package com.ticketingSystem.TicketingSimulation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "configuration_tbl")
@Entity
public class Configuration implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private long id = 1L;
    private int totalTickets ;//Number of tickets released at once
    private int maxTicketCapacity ;//max a pool can hold at a moment
    private int ticketReleaseRate ;
    private int customerRetrievalRate ;



    public Configuration() {
        this.totalTickets = 0;
        this.maxTicketCapacity= 0;
        this.ticketReleaseRate=0;
        this.customerRetrievalRate=0;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", totalTickets=" + totalTickets +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                '}';
    }
}