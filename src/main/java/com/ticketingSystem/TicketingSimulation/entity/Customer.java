package com.ticketingSystem.TicketingSimulation.entity;

import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.controller.SimulationController;
import com.ticketingSystem.TicketingSimulation.validation.AutoIdGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Customer implements Runnable {

    private static final AutoIdGeneration customerAutoIdGeneration = new AutoIdGeneration(0);
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    private final Ticketpool ticketpool;
    private final ArrayList<Ticket> purchasedTickets;
    private final String customerId;
    private boolean isVip;
    private int ticketsPerPurchase;
    private int retrievalInterval;


    public Customer(boolean isVip, int ticketsPerPurchase, Ticketpool ticketPool, Configuration config) {
        this.customerId = customerAutoIdGeneration.generateAutoId("CId");
        this.retrievalInterval = config.getCustomerRetrievalRate();
        this.ticketsPerPurchase = ticketsPerPurchase;
        this.ticketpool = ticketPool;
        this.purchasedTickets = new ArrayList<>();
        this.isVip = isVip;

    }

    public Customer(boolean isVip, int ticketsPerPurchase, int retrievalInterval, Ticketpool ticketPool, Configuration config) {
        this.customerId = customerAutoIdGeneration.generateAutoId("CId");
        this.retrievalInterval = retrievalInterval;
        this.ticketsPerPurchase = ticketsPerPurchase;
        this.ticketpool = ticketPool;
        this.purchasedTickets = new ArrayList<>();
        this.isVip = isVip;

    }

    public boolean getIsVip() {
        return isVip;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public void setTicketsPerPurchase(int ticketsPerPurchase) {
        this.ticketsPerPurchase = ticketsPerPurchase;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getTicketsPerPurchase() {
        return ticketsPerPurchase;
    }

    @Override
    public String toString() {
        return "Customer {" +
                " Id =" + customerId +
                " Is VIP = " + isVip +
                " Tickets Per Purchase = " + ticketsPerPurchase +
                " Ticket Purchase Rate = " + retrievalInterval +
                '}';
    }

    private void setPriorityForVipCustomers(boolean isVip) {
        if (this.isVip) {
            logger.info("Customer {} , Identified as VIP and Setting Priority as Vip Customer", customerId);
            logger.debug("Setting Priority for Vip Customer , Higher Priority TO Thread {}", customerId);
            Thread.currentThread().setPriority(Config.VipPriority);
        } else {
            logger.info("Customer {} , Not Identified as VIP ", customerId);
            logger.debug("Setting up Normal Priority for Customer {}", customerId);
            Thread.currentThread().setPriority(Config.LowPriority);
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName(getCustomerId());
        logger.debug("Thread Renamed to Customer Id");
        setPriorityForVipCustomers(this.isVip());
        boolean isActive = true;

        while (isActive) {
            try {
                Thread.sleep(retrievalInterval * 1000L);

                logger.info("Customer {} , Checking for Available Tickets", customerId);
                ticketpool.removeTicket(this, purchasedTickets);
                synchronized (ticketpool) {
                    if (ticketpool.getLargePoolSize() < this.getTicketsPerPurchase()) {
                        Thread.currentThread().interrupt();
                        logger.info("Customer {} , Insufficient Tickets Available , Customer is Exited from the Pool", customerId);
                        logger.debug("Customer thread is Interrupted ");
                        if (Thread.interrupted()) {
                            logger.info("TicketPool Size {} , LargePool Size {} ", ticketpool.getTicketPoolSize(), ticketpool.getLargePoolSize());
                            logger.debug("TicketPool Size {} , LargePool Size {} ", ticketpool.getTicketPoolSize(), ticketpool.getLargePoolSize());

                            isActive = false;
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted. Exiting...");
                isActive = false;
            }
        }
    }
}