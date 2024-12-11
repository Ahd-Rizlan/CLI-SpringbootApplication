package com.ticketingSystem.TicketingSimulation.entity;

import com.ticketingSystem.TicketingSimulation.validation.AutoIdGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static com.ticketingSystem.TicketingSimulation.webSocketConfig.WebSocketHandler.logAndBrodcastMessage;

public class Customer implements Runnable {

    private static final AutoIdGeneration customerAutoIdGeneration = new AutoIdGeneration(0);
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    private final Ticketpool ticketpool;
    private final ArrayList<Ticket> purchasedTickets;
    private  String customerId;

    private int ticketsPerPurchase;
    private int retrievalInterval;


    public Customer( int ticketsPerPurchase, Ticketpool ticketPool, Configuration config) {
        this.customerId = customerAutoIdGeneration.generateAutoId("CId");
        this.retrievalInterval = config.getCustomerRetrievalRate();
        this.ticketsPerPurchase = ticketsPerPurchase;
        this.ticketpool = ticketPool;
        this.purchasedTickets = new ArrayList<>();


    }

    public Customer( int ticketsPerPurchase, int retrievalInterval, Ticketpool ticketPool, Configuration config) {
        this.customerId = customerAutoIdGeneration.generateAutoId("CId");
        this.retrievalInterval = retrievalInterval;
        this.ticketsPerPurchase = ticketsPerPurchase;
        this.ticketpool = ticketPool;
        this.purchasedTickets = new ArrayList<>();


    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    public void setTicketsPerPurchase(int ticketsPerPurchase) {
        this.ticketsPerPurchase = ticketsPerPurchase;
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
//                " Is VIP = " + isVip +
                " Tickets Per Purchase = " + ticketsPerPurchase +
                " Ticket Purchase Rate = " + retrievalInterval +
                '}';
    }



    @Override
    public void run() {
        Thread.currentThread().setName(getCustomerId());
        boolean isActive = true;

        while (isActive) {
            try {
                Thread.sleep(retrievalInterval * 1000L);

//                logger.info("Customer {} , Checking for Available Tickets", customerId);
                System.out.println("Customer " + customerId + " is checking for available tickets");

                ticketpool.removeTicket(this, purchasedTickets);

                synchronized (ticketpool) {
                    if (ticketpool.getLargePoolSize() < this.getTicketsPerPurchase()) {
                        Thread.currentThread().interrupt();
//logger.info("Customer {} , Insufficient Tickets Available , Customer is Exited from the Pool", customerId);
                      //  System.out.println("Customer " + customerId + " has exited from the pool due to insufficient tickets");
                   //     logger.debug("Customer thread is Interrupted ");
                        if (Thread.interrupted()) {
                            logAndBrodcastMessage("Customer { "+customerId+" } Insufficient Tickets Available , Customer is Exited from the Pool");

//                            logger.info("TicketPool Size {} , LargePool Size {} ", ticketpool.getTicketPoolSize(), ticketpool.getLargePoolSize());
//                            logger.debug("TicketPool Size {} , LargePool Size {} ", ticketpool.getTicketPoolSize(), ticketpool.getLargePoolSize());
//                            System.out.println("Customer " + customerId + " TicketPool Size " + ticketpool.getTicketPoolSize() + " , LargePool Size " + ticketpool.getLargePoolSize());
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