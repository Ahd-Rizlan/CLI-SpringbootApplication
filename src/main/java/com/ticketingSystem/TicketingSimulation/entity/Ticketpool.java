package com.ticketingSystem.TicketingSimulation.entity;


import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.constant.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ticketingSystem.TicketingSimulation.WebSocketConfig.WebSocketHandler.logAndBrodcastMessage;


public class Ticketpool {
    private static final Logger logger = LoggerFactory.getLogger(Ticketpool.class);


    private final List<Ticket> ticketPool;
    private final int totalTickets;
    private final int maxCapacity;
    private int currentSizeOfLargePool;

    private int LargePoolSize ;

    public Ticketpool(Configuration configuration) {
        this.totalTickets = configuration.getTotalTickets();
        this.maxCapacity = configuration.getMaxTicketCapacity();
        this.ticketPool = Collections.synchronizedList(new ArrayList<>(configuration.getMaxTicketCapacity()));
        Config.TotalTicketsToRelease = totalTickets;
        LargePoolSize = Config.TotalTicketsToRelease;
    }

    public int getLargePoolSize() {
        return LargePoolSize;
    }

    public synchronized int addTicketsOnMainPool(Vendor vendor) {

        int tickerCount = (Config.TotalTicketsToRelease / Config.TotalNumberOfVendors);
        logAndBrodcastMessage("Maximum Tickets Released by Vendor :"+ vendor.getVendorId()+" is " +tickerCount);
        return tickerCount;
    }

    public synchronized void addTicket(Vendor vendor, int TotalTicketsToBeReleased) {
        //TODO update total ticket'
        ArrayList<Ticket> tickets = new ArrayList<>();
        if (ticketPool.size() == maxCapacity) {
//            logger.debug("Current Pool Size {} , Maximum TicketPool Capacity {} = Maximum Ticket Pool Capacity Reached", ticketPool.size(), maxCapacity);
            logAndBrodcastMessage("Maximum Pool Capacity Reached " + ticketPool.size());
//            System.out.println("TicketPool - " + "Maximum Pool Capacity Reached " + ticketPool.size());
            try {
                wait();
                logger.info("Waiting Vendor Threads for the Pool to be Updated");
                //TODO
            } catch (InterruptedException e) {
                logger.error("Error Occurred while adding Ticket to the Pool");
                throw new RuntimeException(e);
            }
        } else {
            for (int i = 0; i < TotalTicketsToBeReleased; i++) {
                Ticket ticket = new Ticket(vendor);
                ticket.setStatus(TicketStatus.PENDING);
                tickets.add(ticket);
                currentSizeOfLargePool++;
//                logger.debug("Ticket Added to the Pool : {} by Vendor {} ", ticket, vendor.getVendorId());
            }
            logAndBrodcastMessage(TotalTicketsToBeReleased+" : Tickets were Added to TicketPool By Vendor - "+vendor.getVendorId());

            ticketPool.addAll(tickets);
            logAndBrodcastMessage("Current TicketPool Size : " + ticketPool.size() + " , Remaining Tickets Can be Purchased :" + LargePoolSize);
            notifyAll();


            //TODO LOGG AS TICKET ADDED
        }
    }
    //TODO LOGGING
    public synchronized void removeTicket(Customer customer, ArrayList<Ticket> purchasedTickets) {
        int requiredTickets = customer.getTicketsPerPurchase();
        if (ticketPool.isEmpty() && LargePoolSize!=0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (ticketPool.isEmpty() && LargePoolSize == 0) {
            //I canged Here currentSizeOfLargePool to LargePoolSize

            logAndBrodcastMessage("TicketPool Size : " + ticketPool.size() + " , Available Tickets To Purchase : " + LargePoolSize);
            logAndBrodcastMessage("Tickets are Sold-Out");
            // try to takout
            Thread.currentThread().interrupt();
            if (Thread.interrupted()) {
                logAndBrodcastMessage("Customer "+customer.getCustomerId()+" is Exited as Tickets are Sold Out");
//                System.out.println("Tickets Are Sold Out");
            }

        } else if (ticketPool.size() < customer.getTicketsPerPurchase()) {
            logAndBrodcastMessage("Insufficient Tickets Available On Pool, Customer "+ customer.getCustomerId()+" is Waiting for the Tickets to be Updated");
            notifyAll();
            logger.info("Notifying the Vendor Threads to Update the Pool");
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Error Occurred while Removing Ticket from the Pool");
                throw new RuntimeException(e);
            }
        } else {

            for (int i = 0; i < requiredTickets; i++) {
                ticketPool.getFirst().setStatus(TicketStatus.ACCQUIRED);
                purchasedTickets.add(ticketPool.getFirst());
                ticketPool.removeFirst();
                LargePoolSize--;
            }
            logAndBrodcastMessage("Customer "+customer.getCustomerId()+" has Purchased "+customer.getTicketsPerPurchase()+" Tickets");
        }


    }
    public int getMaxPoolCapacity() {
        return maxCapacity;
    }

    public int getTicketPoolSize() {
        return ticketPool.size();
    }

    public int getTicketPoolCapacity() {
        return totalTickets;
    }

    public int getCurrentSizeOfLargePool() {
        return currentSizeOfLargePool;
    }

    public int getCurrentPoolSizePoolSize() {
        return currentSizeOfLargePool;
    }

    public void setLargePoolSize(int largePoolSize) {
        LargePoolSize = largePoolSize;
    }
    public void setTicketPoolSize(int ticketPoolSize) {
        currentSizeOfLargePool = ticketPoolSize;
    }
}

