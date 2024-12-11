package com.ticketingSystem.TicketingSimulation.DTO;

import com.ticketingSystem.TicketingSimulation.entity.Customer;

public class CustomerDTO {

    private String customerId;
    private String customerType;
    private int ticketsPerPurchase;
    private int retrievalInterval;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.customerId = customer.getCustomerId();
        if (customer.getClass().getSimpleName().equals("VipCustomer")) {
            this.customerType = "VIP";
        } else {
            this.customerType = "Regular";
        }
        this.ticketsPerPurchase = customer.getTicketsPerPurchase();
        this.retrievalInterval = customer.getRetrievalInterval();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
