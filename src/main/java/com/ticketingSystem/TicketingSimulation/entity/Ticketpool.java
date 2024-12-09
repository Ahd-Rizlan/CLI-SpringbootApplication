package com.ticketingSystem.TicketingSimulation.entity;


import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.constant.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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


//    public synchronized int checkVendorEligibility(Vendor vendor) {
//
//        int availableCapacity = this.getTicketPoolCapacity() - this.getPoolSize();
//        int vendorTotalTickets = vendor.getTotalTicketsToRelease();
//
//        //currentPoolSize ==  no change only increase
//        if (availableCapacity == 0) {
//            //TODO LOG ABOUT CONDITION
//            Thread.currentThread().interrupt();
//            if (Thread.interrupted()) {
//                System.out.println("Maximum Event Ticket Capacity Reached");
//                System.out.println("Vendor : " + vendor.getVendorId() + " is Removed");
//            }
//        } else {
//            //TODO LOG AVAILABILTY
//            System.out.println(Math.min(vendorTotalTickets, availableCapacity));
//            int releasableTicketAmount = Math.min(vendorTotalTickets, availableCapacity);
//            PoolSize = PoolSize + releasableTicketAmount;
//            currentPoolSize = currentPoolSize + releasableTicketAmount;
//            if (vendorTotalTickets > availableCapacity) {
//                System.out.println("Vendor " + " - " + vendor.getVendorId() + " is Released " + availableCapacity + " Tickets : Since Maximum Event Ticket Capacity Reached");
//                //TODO LOG ABOUT CONDITION
//
//            } else {
//                System.out.println("Vendor " + " - " + vendor.getVendorId() + " is Released " + vendorTotalTickets + " Tickets.");
//                //TODO LOG ABOUT CONDITION
//            }
//        }
//        return Math.min(vendorTotalTickets, availableCapacity);
//    }

//----------------------------------

//----------------------------------

    // to check ticket pool Capacity
//    public synchronized int ticketPoolCapacityCheck() {
//        return getMaxPoolCapacity() - getTicketPoolSize();
//
//    }


    public int getLargePoolSize() {
        return LargePoolSize;
    }

    public void setLargePoolSize(int largePoolSize) {
        LargePoolSize = largePoolSize;
    }

    public synchronized int addTicketsOnMainPool(Vendor vendor) {

        int tickerCount = (Config.TotalTicketsToRelease / Config.TotalNumberOfVendors);
        logger.debug("Total Tickets to be released : {}", Config.TotalTicketsToRelease);
        logger.debug("Total Number of Vendors : {}", Config.TotalNumberOfVendors);
        logger.debug("Tickets to be released by each Vendor : {}", tickerCount);

        //updating the total number of tickets
        //   currentSizeOfLargePool = currentSizeOfLargePool + tickerCount;

        //   System.out.println("Vendor trying to add " + tickerCount);
        logger.info("Maximum Tickets Released by Vendor : {} is {} ", vendor.getVendorId(), tickerCount);

        //  ArrayList<Ticket> TotalTicketsToBeReleased = new ArrayList<>();
        //  System.out.println("Vendor Added " + tickerCount + " Tickets");
        return tickerCount;
    }

//    public synchronized int addTicketsOnMainPool(Configuration configuration) {
//        int tickerCount = (Config.TotalTicketsToRelease / Config.TotalNumberOfVendors);
//        System.out.println("Vendor trying to add " + tickerCount);
//        //  ArrayList<Ticket> TotalTicketsToBeReleased = new ArrayList<>();
//        int TotalTicketsToBeReleased = tickerCount;
//        System.out.println(configuration.getVendorId());
//        System.out.println("Vendor Added " + tickerCount + " Tickets");
////        TotalTicketsToBeReleased.size();
////        return TotalTicketsToBeReleased;
//
//    }


//    public synchronized void addTicket(Vendor vendor, ArrayList<Ticket> tickets) {
//        //TODO update total ticket'
//
//        if (ticketPool.size() == maxCapacity) {
//            System.out.println("TicketPool - " + "Maximum Pool Capacity Reached " + ticketPool.size());
//            try {
//                wait();
//                //TODO
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            ticketPool.addAll(tickets);
//            System.out.println("Vendor" + " : " + vendor.getVendorId() + " Added " + tickets.size() + " tickets : " + "Updated TicketPool Size :" + ticketPool.size());
//            notifyAll();
//            //TODO LOGG AS TICKET ADDED
//        }
//    }

    public synchronized void addTicket(Vendor vendor, int TotalTicketsToBeReleased) {
//        if (TotalTicketsToBeReleased == 0) {
//            Thread.currentThread().interrupt();
//            if (Thread.interrupted()) {
//                System.out.println("Vendor " + vendor.getVendorId() + " : " + "Tickets are Sold Out--------------------------------------------------------------------------------------------------------------------------");
//            }
//        }
        //TODO update total ticket'
        ArrayList<Ticket> tickets = new ArrayList<>();
        if (ticketPool.size() == maxCapacity) {
            logger.info("TicketPool Size {} ", ticketPool.size());
            logger.info("Maximum Pool Capacity Reached {} ", ticketPool.size());
            logger.debug("Current Pool Size {} , Maximum TicketPool Capacity {} = Maximum Ticket Pool Capacity Reached", ticketPool.size(), maxCapacity);
//            System.out.println("TicketPool - " + "Maximum Pool Capacity Reached " + ticketPool.size());
            try {
                wait();
                logger.debug("Waiting Vendor Threads for the Pool to be Updated");
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
                logger.debug("Ticket Added to the Pool : {} by Vendor {} ", ticket, vendor.getVendorId());
            }
            logger.debug("Total Tickets to be released by Vendor {} is {}", vendor.getVendorId(), TotalTicketsToBeReleased);
            logger.info("{} Tickets were Added to TicketPool By Vendor{}", TotalTicketsToBeReleased, vendor.getVendorId());
            ticketPool.addAll(tickets);
            logger.info("Current TicketPool Size : {} , Remaining Tickets to be Purchased {}", ticketPool.size(), LargePoolSize);
            //  currentSizeOfLargePool = currentSizeOfLargePool + TotalTicketsToBeReleased;
//            System.out.println("Vendor" + " : " + vendor.getVendorId() + " Added " + tickets.size() + " tickets : " + "Updated TicketPool Size :" + ticketPool.size());
            notifyAll();
            logger.debug("Notifying the Customer Threads");

            //TODO LOGG AS TICKET ADDED
        }
    }
//
//    private synchronized ArrayList<Ticket> changeTicketStatusToSold(int tickerCount, ArrayList<Ticket> soldTickets) {
//        synchronized (ticketPool) {
//            for (int i = ticketPool.size() - 1; i >= 0 && tickerCount > 0; i--) {
//                if (ticketPool.get(i).getStatus() == TicketStatus.OnPOOL) {
//                    ticketPool.get(i).setStatus(TicketStatus.ACCQUIRED);
//                    soldTickets.add(ticketPool.get(i));
//                    ticketPool.remove(i); // Safe to remove when iterating backward
//                    tickerCount--; // Decrement tickets to process
//                }
//            }
//            return soldTickets;
//        }
//}


//    public synchronized void removeTicketToTotalCapacity(int purchasedTicketAmount) {
//        if (currentPoolSize >= purchasedTicketAmount) {
//            currentPoolSize -= purchasedTicketAmount;
//        }
//    }


    //TODO LOGGING
    public synchronized void removeTicket(Customer customer, ArrayList<Ticket> purchasedTickets) {
        int requiredTickets = customer.getTicketsPerPurchase();
        logger.debug("Customer {} is Required {} tickets", customer.getCustomerId(), requiredTickets);
        if (ticketPool.isEmpty() && LargePoolSize == 0) {
            //I canged Here currentSizeOfLargePool to LargePoolSize

            logger.info("TicketPool Size {} , LargePool Size {} ", ticketPool.size(), LargePoolSize);
            logger.info("Tickets are Sold Out");
            Thread.currentThread().interrupt();
            if (Thread.interrupted()) {
                logger.debug("Customer {} Thread is Interrupted to stop the Thread", customer.getCustomerId());
                logger.info("Customer {} is Exited as Tickets are Sold Out", customer.getCustomerId());
//                System.out.println("Tickets Are Sold Out");
            }

        } else if (ticketPool.size() < customer.getTicketsPerPurchase()) {
//            System.out.println("-----------------------------------------------------===  " + LargePoolSize);
//            if (LargePoolSize < customer.getTicketsPerPurchase()) {
//                System.out.println("Insufficient Tickets Available---------------------------------------------------------=======");
//                System.out.println(Thread.currentThread().getName());
//
//                Thread.currentThread().interrupt();
//                if (Thread.interrupted()) {
//                    System.out.println(Thread.currentThread().getName());
//                    System.out.println("Tickets Are Sold Out");
//                }
//
//            }
            logger.debug("TicketPool Size {} , Customer : {} Required Ticket {} ", ticketPool.size(), customer.getCustomerId(), customer.getTicketsPerPurchase());
            logger.debug("Insufficient Tickets Available On Pool, Customer {} is Waiting for the Tickets to be Updated", customer.getCustomerId());

            logger.info("TicketPool Size {} , Customer : {} Required Ticket {} ", ticketPool.size(), customer.getCustomerId(), customer.getTicketsPerPurchase());
            logger.info("Insufficient Tickets Available On Pool, Customer {} is Waiting for the Tickets to be Updated", customer.getCustomerId());
//            System.out.println("TicketPool : Please wait while tickets are being updated.");
//            System.out.println("TicketPool Available Tickets : " + ticketPool.size());

            //TODO Log
            notifyAll();
            logger.debug("Notifying the Vendor Threads to Update the Pool");
            try {
                wait();
                logger.debug(" Customer Thread {} Waiting for the Pool to be Updated", customer.getCustomerId());
            } catch (InterruptedException e) {
                logger.error("Error Occurred while Removing Ticket from the Pool");
                throw new RuntimeException(e);
            }
        } else {
            // currentSizeOfLargePool = currentSizeOfLargePool - requiredTickets;

            for (int i = 0; i < requiredTickets; i++) {
                ticketPool.get(0).setStatus(TicketStatus.ACCQUIRED);
                purchasedTickets.add(ticketPool.get(0));
                ticketPool.remove(0);
                LargePoolSize--;
            }
//            System.out.println("Customer" + customer.getCustomerId() + " - " + " Purchased " + customer.getTicketsPerPurchase() + " tickets ;" + "Remaining Tickets Available :" + ticketPool.size());
            logger.info("Customer {} is Purchasing {} Tickets", customer.getCustomerId(), customer.getTicketsPerPurchase());
            logger.info("TicketPool Size {} , LargePool Size {} ", ticketPool.size(), LargePoolSize);
        }


    }

    //    public synchronized void removeTicket(Customer customer, ArrayList<Ticket> purchasedTickets) {
//        int requiredTickets = customer.getTicketsPerPurchase();
//        purchasedTickets.addAll(changeTicketStatusToSold(requiredTickets, new ArrayList<>()));
//        System.out.println("Customer " + " - " + customer.getCustomerId() + " : " + " Purchased " + customer.getTicketsPerPurchase() + " tickets ;" + "Remaining Tickets Available :" + ticketPool.size());
//        if (ticketPool.size() == 0 && currentSizeOfLargePool == 0) {
//            Thread.currentThread().interrupt();
//            if (Thread.interrupted()) {
//                System.out.println("Tickets Are Sold Out");
//            }
//        }
//        if (ticketPool.size() < requiredTickets) {
//            System.out.println("TicketPool : Please wait while tickets are being updated.");
//            System.out.println("TicketPool Available Tickets : " + ticketPool.size());
//            //TODO Log
//            notifyAll();
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
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
}

