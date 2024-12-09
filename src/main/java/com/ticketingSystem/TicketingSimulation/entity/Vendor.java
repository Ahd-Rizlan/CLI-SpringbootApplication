package com.ticketingSystem.TicketingSimulation.entity;



import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.validation.AutoIdGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Vendor implements Runnable {



    private static final AutoIdGeneration vendorAutoIdGeneration = new AutoIdGeneration(0);
    private static final Logger logger = LoggerFactory.getLogger(Vendor.class);
    private final Ticketpool ticketpool;

    private final String vendorId;
    private final int frequency;
    private int totalTicketsToRelease = 0;
    private int ticketsPerRelease;



    public Vendor(int ticketsPerRelease,  Ticketpool ticketpool, Configuration config,int totalTicketsToRelease) {
        this.vendorId = vendorAutoIdGeneration.generateAutoId("VId");
        this.frequency = config.getTicketReleaseRate();
        this.ticketsPerRelease = ticketsPerRelease;
        this.releasingTickets = new ArrayList<>();
        this.totalTicketsToRelease = totalTicketsToRelease;
        this.ticketpool = ticketpool;

    }

    public int getFrequency() {
        return frequency;
    }

    public String getVendorId() {
        return vendorId;
    }

    public int getTotalTicketsToRelease() {
        return totalTicketsToRelease;
    }

    public void setTotalTicketsToRelease(int totalTicketsToRelease) {
        this.totalTicketsToRelease = totalTicketsToRelease;
    }

    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }
    private final ArrayList<Ticket> releasingTickets;

    public ArrayList<Ticket> getReleasingTickets() {
        return releasingTickets;
    }
//    public int getTotalTickets() {
//        return releasingTickets.size();
//    }

    @Override
    public String toString() {
        return "Vendor{ " +
                "Id =" + vendorId +
                " Total Tickets To Release = " + totalTicketsToRelease +
                " Tickets Per Release = " + ticketsPerRelease +
                " Ticket Release Rate =" + frequency +
                '}';
    }


    @Override
    public void run() {
        Thread.currentThread().setName(getVendorId());
        Thread.currentThread().setPriority(Config.LowPriority);

        // Release Tickets List
        int totalTicketsForRelease;

        //No of TOTAL TICKETS TO BE RELEASED
        totalTicketsForRelease = ticketpool.addTicketsOnMainPool(this);
        // System.out.println("Vendor " + vendorId + " is Releasing " + totalTicketsForRelease + " Tickets");

        //Setting the Total Tickets to be Released
        this.setTotalTicketsToRelease(totalTicketsForRelease);
        logger.debug("Setting the Total Tickets to be Released by {} = {}", vendorId, this.getTotalTicketsToRelease());
        //  System.out.println("Total Tickets to be Released by " + vendorId + " = " + this.getTotalTicketsToRelease());

        // boolean IsActive = true;
        logger.debug("Vendor {} is Releasing Tickets in to the pool until the Maximum Tickets are {} Over , on the rate of {} and in a Interval of {}", vendorId, this.getTotalTicketsToRelease(), this.getTicketsPerRelease(), this.frequency);
        while (totalTicketsForRelease > 0) {
            try {

                synchronized (ticketpool) {
                    int releasableTickets = Math.min(ticketsPerRelease, (ticketpool.getMaxPoolCapacity() - ticketpool.getTicketPoolSize()));
                    releasableTickets = Math.min(releasableTickets, totalTicketsForRelease);

                    logger.info("Tickets Per Release = {} / Tickets in Hand = {} /Available Pool Capacity = {}", ticketsPerRelease, totalTicketsForRelease, releasableTickets);
                    logger.info("Vendor {} is Releasing the minimum amount from the above values {} Tickets to the Pool", vendorId, releasableTickets);
                    logger.info("Tickets Per Release = {} / Tickets in Hand = {} /Available Pool Capacity = {}", ticketsPerRelease, totalTicketsForRelease, releasableTickets);

                    if (releasableTickets == 0) {
                        logger.info("Ticket pool is full , Waiting for the Tickets to be Purchased");

                    } else {
                        logger.info("Vendor {} Released {} Tickets to the Ticket Pool", vendorId, releasableTickets);
                        ticketpool.addTicket(this, releasableTickets);


                        //Substracting the releasable tickets from the total tickets
                        logger.debug("Vendor {} is Substracting the releasable tickets {} from the total tickets {}", vendorId, releasableTickets, totalTicketsForRelease);
                        totalTicketsForRelease -= releasableTickets;
                        logger.debug("Remaining Tickets to Release for Vendor {} is {}", vendorId, totalTicketsForRelease);

                    }
                    if (totalTicketsForRelease == 0) {
                        logger.info("Vendor {} has released all the tickets", vendorId);
                        logger.info("Total Released Tickets by Vendor {} is {}", vendorId, this.getTotalTicketsToRelease());
                        Thread.currentThread().interrupt();
                        if (Thread.interrupted()) {
                            logger.info("Ticket Release is Completed for Vendor {}", vendorId);
                            logger.info("Vendor {} is Released from the Pool ", vendorId);

                        }
                    }

                }

                Thread.sleep(frequency * 1000L);

            } catch (InterruptedException e) {
                logger.error("Ticket release interrupted for Vendor: {}", vendorId);
                System.out.println("Ticket release interrupted for Vendor: " + vendorId);
                Thread.currentThread().interrupt();
                break;

            }
        }

        //add releasable tickets to TotalTickets


        //      TODO logging the Amount of ticket added amount


    }

}
