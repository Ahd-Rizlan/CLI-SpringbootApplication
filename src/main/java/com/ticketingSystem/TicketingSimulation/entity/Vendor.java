package com.ticketingSystem.TicketingSimulation.entity;



import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.validation.AutoIdGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static com.ticketingSystem.TicketingSimulation.webSocketConfig.WebSocketHandler.logAndBrodcastMessage;

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


        // Release Tickets List
        int totalTicketsForRelease;

        //No of TOTAL TICKETS TO BE RELEASED
        totalTicketsForRelease = ticketpool.addTicketsOnMainPool(this);
        this.setTotalTicketsToRelease(totalTicketsForRelease);
        // System.out.println("Vendor " + vendorId + " is Releasing " + totalTicketsForRelease + " Tickets");

        //Setting the Total Tickets to be Released

//        logger.debug("Setting the Total Tickets to be Released by {} = {}", vendorId, this.getTotalTicketsToRelease());
//System.out.println("Setting the Total Tickets to be Released by "+vendorId+" = "+this.getTotalTicketsToRelease());
        //  System.out.println("Total Tickets to be Released by " + vendorId + " = " + this.getTotalTicketsToRelease());

        // boolean IsActive = true;
        //logger.debug("Vendor {} is Releasing Tickets in to the pool until the Maximum Tickets are {} Over , on the rate of {} and in a Interval of {}", vendorId, this.getTotalTicketsToRelease(), this.getTicketsPerRelease(), this.frequency);
//        System.out.println("Vendor "+vendorId+" is Releasing Tickets in to the pool until the Maximum Tickets are "+this.getTotalTicketsToRelease()+" Over , on the rate of "+this.getTicketsPerRelease()+" and in a Interval of "+ this.frequency);
        while (totalTicketsForRelease > 0) {
            try {

                synchronized (ticketpool) {
                    int releasableTickets = Math.min(ticketsPerRelease, (ticketpool.getMaxPoolCapacity() - ticketpool.getTicketPoolSize()));
                    releasableTickets = Math.min(releasableTickets, totalTicketsForRelease);
                    //Check the Minimum Amount can be released to the pool

                    //logger.info("Tickets Per Release = {} / Tickets in Hand = {} /Available Pool Capacity = {}", ticketsPerRelease, totalTicketsForRelease, releasableTickets);
                   // System.out.println("Vendor "+vendorId+" Tickets Per Release = "+ticketsPerRelease+" / Tickets in Hand = "+totalTicketsForRelease+" /Available Pool Capacity = "+releasableTickets);
                   // logger.info("Vendor {} is Releasing the minimum amount from the above values {} Tickets to the Pool", vendorId, releasableTickets);
                   // System.out.println("Vendor "+vendorId+" is Releasing the minimum amount from the above values "+releasableTickets+" Tickets to the Pool");
                    //logger.info("Tickets Per Release = {} / Tickets in Hand = {} /Available Pool Capacity = {}", ticketsPerRelease, totalTicketsForRelease, releasableTickets);
                   // System.out.println("Vendor "+vendorId+" Tickets Per Release = "+ticketsPerRelease+" / Tickets in Hand = "+totalTicketsForRelease+" /Available Pool Capacity = "+releasableTickets);
                    if (releasableTickets == 0) {
                       // logger.info("Ticket pool is full , Waiting for the Tickets to be Purchased");
                       // System.out.println("Vendor "+vendorId+" Ticket pool is full , Waiting for the Tickets to be Purchased");
                        logAndBrodcastMessage("Vendor { "+vendorId+" } Ticket pool is full , Waiting for the Tickets to be Purchased");
                    } else {
                        //logger.info("Vendor {} Released {} Tickets to the Ticket Pool", vendorId, releasableTickets);
                        System.out.println("Vendor "+vendorId+" Released "+releasableTickets+" Tickets to the Ticket Pool");


                        ticketpool.addTicket(this, releasableTickets);


                        //Substracting the releasable tickets from the total tickets
                       // logger.debug("Vendor {} is Substracting the releasable tickets {} from the total tickets {}", vendorId, releasableTickets, totalTicketsForRelease);
//                        System.out.println("Vendor "+vendorId+" is Subtracting the releasable tickets "+releasableTickets+" from the total tickets "+totalTicketsForRelease);
                        totalTicketsForRelease -= releasableTickets;
                        //logger.debug("Remaining Tickets to Release for Vendor {} is {}", vendorId, totalTicketsForRelease);
                      //  System.out.println("Vendor "+vendorId+" Added "+releasableTickets+" Tickets to the Pool , Remaining Tickets to Release is "+totalTicketsForRelease);
                        logAndBrodcastMessage("Vendor "+vendorId+" Added "+releasableTickets+" Tickets to the Pool , Remaining Tickets to Release is "+totalTicketsForRelease);

                    }
                    if (totalTicketsForRelease == 0) {
                        //logger.info("Vendor {} has released all the tickets", vendorId);
                        System.out.println("Vendor "+vendorId+" has released all the tickets");
                        //logger.info("Total Released Tickets by Vendor {} is {}", vendorId, this.getTotalTicketsToRelease());
                        System.out.println("Total Released Tickets by Vendor "+vendorId+" is "+this.getTotalTicketsToRelease());

                        logAndBrodcastMessage("Vendor "+vendorId+" has released all the tickets : Total Released Tickets is "+this.getTotalTicketsToRelease());

                        Thread.currentThread().interrupt();
                        if (Thread.interrupted()) {
                          //  logger.info("Ticket Release is Completed for Vendor {}", vendorId);
                         //   System.out.println("Ticket Release is Completed for Vendor "+vendorId);
                            logAndBrodcastMessage("Ticket Release is Completed for Vendor "+vendorId);
                         //   logger.info("Vendor {} is Released from the Pool ", vendorId);
//System.out.println("Vendor "+vendorId+" is Released from the Pool ");

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
