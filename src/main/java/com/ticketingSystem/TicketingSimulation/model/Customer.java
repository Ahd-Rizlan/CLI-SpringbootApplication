package com.ticketingSystem.TicketingSimulation.model;

import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.validation.AutoIdGeneration;

import java.util.ArrayList;

public class Customer {


        private static final AutoIdGeneration customerAutoIdGeneration = new AutoIdGeneration(0);
        private final Ticketpool ticketpool;
        private final ArrayList<Ticket> purchasedTickets;

        private String customerId;
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


        public boolean isVip() {
            return isVip;
        }

        public String getCustomerId() {
            return customerId;
        }

        public int getTicketsPerPurchase() {
            return ticketsPerPurchase;
        }

        public int getPurchasedTickets() {
            return purchasedTickets.size();
        }

        @Override
        public String toString() {
            return "Customer {" +
                    "Id =" + customerId +
                    "Tickets Per Purchase = " + ticketsPerPurchase +
                    "Ticket Purchase Rate =" + retrievalInterval +
                    '}';
        }


        private void setPriorityForVipCustomers(boolean isVip) {
            if (this.isVip == true) {
                Thread.currentThread().setPriority(Config.VipPriority);
            } else {
                Thread.currentThread().setPriority(Config.LowPriority);
            }
        }

//        @Override
//        public void run() {
//            Thread.currentThread().setName(getCustomerId());
//            setPriorityForVipCustomers(this.isVip());
//            boolean isActive = true;
//
//
//            while (isActive) {
//                try {
//                    if (isActive) {
//                        Thread.sleep(retrievalInterval * 1000L);
//                        synchronized (ticketpool) {
//                            int availableTickets = ticketpool.getCurrentPoolSizePoolSize();
//                            // System.out.println("------------------------------------------------------------------------------------------");
//                            // System.out.println("Available Tickets  = " + availableTickets);
//                            //System.out.println(Thread.currentThread().getName() + " Amount To be Purchased = " + getTicketsPerPurchase());
//                            if (availableTickets == 0) {
//                                Thread.currentThread().interrupt();
//                                if (Thread.interrupted()) {
//                                    System.out.println("Exitning The Customer " + Thread.currentThread().getName() + " Total Tickets Purchased : " + this.getPurchasedTickets() + " : Tickets Sold-out");
//                                    isActive = false;
//                                }
//                            } else if (availableTickets >= getTicketsPerPurchase()) {
//                                ticketpool.removeTicketToTotalCapacity(getTicketsPerPurchase());
//                                ticketpool.removeTicket(this, purchasedTickets);
//                                //System.out.println("---------------------------------------------------------------------------------");
//                                //TODO LOG HERE THE BOTH AMOUNTS
//                            } else {
//                                System.out.println("Customer : " + customerId + " Insufficient amount of tickets");
////                            System.out.println("TicktPool : " + ticketpool.getTicketPoolSize());
//                                ticketpool.wait();
//                                ticketpool.notifyAll();
//                            }
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    System.out.println(Thread.currentThread().getName() + " was interrupted. Exiting...");
//                    isActive = false;
//                }
//            }
//        }
    }