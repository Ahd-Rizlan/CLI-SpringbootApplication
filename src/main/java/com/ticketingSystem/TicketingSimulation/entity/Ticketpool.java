package com.ticketingSystem.TicketingSimulation.entity;


import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.constant.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ticketingSystem.TicketingSimulation.webSocketConfig.WebSocketHandler.logAndBrodcastMessage;


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

//        System.out.println("Total Tickets to be released : " + Config.TotalTicketsToRelease);
////        logger.debug("Total Tickets to be released : {}", Config.TotalTicketsToRelease);
//        System.out.println("Total Number of Vendors : " + Config.TotalNumberOfVendors);
////        logger.debug("Total Number of Vendors : {}", Config.TotalNumberOfVendors);
////        System.out.println("Tickets to be released by each Vendor : " + tickerCount);
////        logger.debug("Tickets to be released by each Vendor : {}", tickerCount);


//        logger.info("Maximum Tickets Released by Vendor : {} is {} ", vendor.getVendorId(), tickerCount);
//        System.out.println("Maximum Tickets Released by Vendor : " + vendor.getVendorId() + " is " + tickerCount);
        logAndBrodcastMessage("Maximum Tickets Released by Vendor : " + vendor.getVendorId() + " is " + tickerCount);

        return tickerCount;
    }

    public synchronized void addTicket(Vendor vendor, int TotalTicketsToBeReleased) {
        //TODO update total ticket'
        ArrayList<Ticket> tickets = new ArrayList<>();
        if (ticketPool.size() == maxCapacity) {
            logAndBrodcastMessage("Maximum Pool Capacity Reached " + ticketPool.size());

           // System.out.println("TicketPool Size " + ticketPool.size());
            //logger.info("TicketPool Size {} ", ticketPool.size());
            //System.out.println("Maximum Pool Capacity Reached " + ticketPool.size());
            //logger.info("Maximum Pool Capacity Reached {} ", ticketPool.size());
            //System.out.println("Current Pool Size " + ticketPool.size() + " , Maximum TicketPool Capacity " + maxCapacity + " = Maximum Ticket Pool Capacity Reached");
          //  logger.debug("Current Pool Size {} , Maximum TicketPool Capacity {} = Maximum Ticket Pool Capacity Reached", ticketPool.size(), maxCapacity);
//           System.out.println("TicketPool - " + "Maximum Pool Capacity Reached " + ticketPool.size());
            try {
                wait();
//                logger.debug("Waiting Vendor Threads for the Pool to be Updated");
                logAndBrodcastMessage("Waiting for Customers to Purchase Tickets");
//                System.out.println("Waiting Vendor Threads for the Pool to be Updated");
                //TODO
            } catch (InterruptedException e) {
//                System.out.println("Error Occurred while adding Ticket to the Pool");
//                logger.error("Error Occurred while adding Ticket to the Pool");
                throw new RuntimeException(e);
            }
        } else {
            for (int i = 0; i < TotalTicketsToBeReleased; i++) {
                Ticket ticket = new Ticket(vendor);
                ticket.setStatus(TicketStatus.PENDING);
                tickets.add(ticket);
                currentSizeOfLargePool++;
//                logger.debug("Ticket Added to the Pool : {} by Vendor {} ", ticket, vendor.getVendorId());
//                System.out.println("Ticket Added to the Pool : " + ticket + " by Vendor " + vendor.getVendorId());
            }
//            System.out.println("Total Tickets to be released by Vendor " + vendor.getVendorId() + " is " + TotalTicketsToBeReleased);


//            logger.debug("Total Tickets to be released by Vendor {} is {}", vendor.getVendorId(), TotalTicketsToBeReleased);
//            System.out.println(TotalTicketsToBeReleased + " Tickets were Added to TicketPool By Vendor" + vendor.getVendorId());

//            logger.info("{} Tickets were Added to TicketPool By Vendor{}", TotalTicketsToBeReleased, vendor.getVendorId());
            ticketPool.addAll(tickets);
//            logAndBrodcastMessage(TotalTicketsToBeReleased + " Tickets were Added to TicketPool By Vendor" + vendor.getVendorId());
//            logAndBrodcastMessage("Current TicketPool Size : " + ticketPool.size() + " , Remaining Tickets to be Purchased " + LargePoolSize);


//            System.out.println("Current TicketPool Size : " + ticketPool.size() + " , Remaining Tickets to be Purchased " + LargePoolSize);
//            logger.info("Current TicketPool Size : {} , Remaining Tickets to be Purchased {}", ticketPool.size(), LargePoolSize);
            //  currentSizeOfLargePool = currentSizeOfLargePool + TotalTicketsToBeReleased;
//            System.out.println("Vendor" + " : " + vendor.getVendorId() + " Added " + tickets.size() + " tickets : " + "Updated TicketPool Size :" + ticketPool.size());
            notifyAll();
//            logger.debug("Notifying the Customer Threads");
            System.out.println("Notifying the Customer Threads");

            //TODO LOGG AS TICKET ADDED
        }
    }
    //TODO LOGGING
    public synchronized void removeTicket(Customer customer, ArrayList<Ticket> purchasedTickets) {
        int requiredTickets = customer.getTicketsPerPurchase();
        //logger.debug("Customer {} is Required {} tickets", customer.getCustomerId(), requiredTickets);
      //  System.out.println("Customer " + customer.getCustomerId() + " is Required " + requiredTickets + " tickets");
        logAndBrodcastMessage("Customer " + customer.getCustomerId() + " is Checking Availability for  " + requiredTickets + " tickets");
//
       if (ticketPool.size() < customer.getTicketsPerPurchase()) {
//            logger.debug("TicketPool Size {} , Customer : {} Required Ticket {} ", ticketPool.size(), customer.getCustomerId(), customer.getTicketsPerPurchase());
           // System.out.println("TicketPool Size " + ticketPool.size() + " , Customer : " + customer.getCustomerId() + " Required Ticket " + customer.getTicketsPerPurchase());
//            logger.debug("Insufficient Tickets Available On Pool, Customer {} is Waiting for the Tickets to be Updated", customer.getCustomerId());
     //       System.out.println("Insufficient Tickets Available On Pool, Customer " + customer.getCustomerId() + " is Waiting for the Tickets to be Updated");
//            logger.info("TicketPool Size {} , Customer : {} Required Ticket {} ", ticketPool.size(), customer.getCustomerId(), customer.getTicketsPerPurchase());
         //   System.out.println("TicketPool Size " + ticketPool.size() + " , Customer : " + customer.getCustomerId() + " Required Ticket " + customer.getTicketsPerPurchase());
            logAndBrodcastMessage("Insufficient Tickets Available On Pool "+"{TicketPool Size " + ticketPool.size() + " } ,Total Tickets Available { "+LargePoolSize+" } Customer " + customer.getCustomerId() + " is Waiting for the Tickets to be Updated");
//            logger.info("Insufficient Tickets Available On Pool, Customer {} is Waiting for the Tickets to be Updated", customer.getCustomerId());
      //      System.out.println("Insufficient Tickets Available On Pool, Customer " + customer.getCustomerId() + " is Waiting for the Tickets to be Updated");
            //TODO Log
            notifyAll();
         //   System.out.println("Notifying the Vendor Threads to Update the Pool");
//            logAndBrodcastMessage("Notifying the Vendor Threads to Update the Pool");
//            logger.debug("Notifying the Vendor Threads to Update the Pool");
            try {
                wait();
//                logger.debug(" Customer Thread {} Waiting for the Pool to be Updated", customer.getCustomerId());
//                System.out.println("Customer Thread " + customer.getCustomerId() + " Waiting for the Pool to be Updated");
                logger.debug("Notifying the Vendor Threads to Update the Pool");
            } catch (InterruptedException e) {
                logger.error("Error Occurred while Removing Ticket from the Pool");
//                logger.error("Error Occurred while Removing Ticket from the Pool");
                throw new RuntimeException(e);
            }
        } else {

            for (int i = 0; i < requiredTickets; i++) {
                ticketPool.getFirst().setStatus(TicketStatus.ACCQUIRED);
                purchasedTickets.add(ticketPool.getFirst());
                ticketPool.removeFirst();
                LargePoolSize--;
            }
           // System.out.println("Customer" + customer.getCustomerId() + " - " + " Purchased " + customer.getTicketsPerPurchase() + " Tickets");
            logAndBrodcastMessage("Customer" + customer.getCustomerId() + " - " + " Purchased " + customer.getTicketsPerPurchase() + " Tickets");
//            logger.info("Customer {} is Purchasing {} Tickets", customer.getCustomerId(), customer.getTicketsPerPurchase());
         //   System.out.println("Customer " + customer.getCustomerId() + " is Purchasing " + customer.getTicketsPerPurchase() + " Tickets");
//            logger.info("TicketPool Size {} , LargePool Size {} ", ticketPool.size(), LargePoolSize);
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

