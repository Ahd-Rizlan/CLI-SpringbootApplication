package com.ticketingSystem.TicketingSimulation.model;

import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.constant.TicketStatus;
import com.ticketingSystem.TicketingSimulation.validation.AutoIdGeneration;
import jakarta.persistence.*;

import java.util.ArrayList;
@Table(name = "Vendor_tbl")
@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  String vendorId;
    private static final AutoIdGeneration vendorAutoIdGeneration = new AutoIdGeneration(0);
    private  int frequency;
    private  int totalTicketsToRelease;
    private int ticketsPerRelease;

    @PrePersist
    private void generateVendorId() {
        if (this.vendorId == null || this.vendorId.isEmpty()) {
            this.vendorId = "Vendor-" + vendorAutoIdGeneration.generateAutoId("VID"); // Example: Vendor-1234abcd
        }
    }
    public Vendor() {}


//    public Vendor(int totalTicketsToRelease, int ticketsPerRelease, Ticketpool ticketpool, Configuration config) {
//        this.vendorId = vendorAutoIdGeneration.generateAutoId("VId");
//        this.frequency = config.getTicketReleaseRate();
//        this.ticketsPerRelease = ticketsPerRelease;
//        this.totalTicketsToRelease = totalTicketsToRelease;
//      ;
//
//    }
//
//    public Vendor(int totalTicketsToRelease, int ticketsPerRelease, int frequency, Ticketpool ticketpool, Configuration config) {
//        this.vendorId = vendorAutoIdGeneration.generateAutoId("VId");
//        this.frequency = frequency;
//        this.ticketsPerRelease = ticketsPerRelease;
//        this.totalTicketsToRelease = totalTicketsToRelease;
//
//
//    }


    public String getVendorId() {
        return vendorId= vendorAutoIdGeneration.generateAutoId("VId");
    }

    public int getTotalTicketsToRelease() {
        return totalTicketsToRelease;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
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
//
//    public int getTotalTickets() {
//        return releasingTickets.size();
//    }

//    @Override
//    public String toString() {
//        return "Vendor{" +
//                "Id =" + vendorId +
//                "Total Tickets To Release = " + totalTicketsToRelease +
//                "Tickets Per Release = " + ticketsPerRelease +
//                "Ticket Release Rate =" + frequency +
//                '}';
//    }

//    public boolean getVendorStatus() {
//        boolean status = true;
//        if (getUnReleasingTickets() == 0) {
//            status = false;
//            Thread.currentThread().interrupt();
//            if (Thread.interrupted()) {
//                System.out.println("Vendor " + " - " + Thread.currentThread().getName() + " : " + "Total Released Tickets : " + getTotalTickets());
//                System.out.println("Vendor " + " - " + Thread.currentThread().getName() + " : " + "Ticket Release is Completed");
//            }
//
//        }
//        return status;
//    }
//
//    public int getUnReleasingTickets() {
//        int unReleasedTickets = 0;
//        for (Ticket ticket : releasingTickets) {
//            if (ticket.getStatus() == TicketStatus.PENDING) {
//                unReleasedTickets++;
//            }
//        }
//        return unReleasedTickets;
//    }

//    private void addToTempListFromVendorList(int tickerCount, ArrayList<Ticket> ticketsToPool) {
//        int ChangedTickets = tickerCount;
//        for (int i = 0; i < releasingTickets.size(); i++) {
//            if (releasingTickets.get(i).getStatus() == TicketStatus.PENDING) {
//                releasingTickets.get(i).setStatus(TicketStatus.OnPOOL);
//                ticketsToPool.add(releasingTickets.get(i));
//                ChangedTickets--;
//                if (ChangedTickets == 0) {
//                    return;
//                }
//            }
//        }
//    }
//
//    private boolean releasableTicketsToMainPool() {
//        int releasableTicketCapacity;
//        releasableTicketCapacity = ticketpool.checkVendorEligibility(this);
//        //check the amount of tickets can be added
//        if (releasableTicketCapacity == 0) {
//            return false;
//        } else {
//            //TODO LOG AMOUNT OF TICKETS RELEASED
//            //Creating Released Tickets and add it to the Main List
//            for (int i = 0; i < releasableTicketCapacity; i++) {
//                Ticket ticket = new Ticket(Vendor.this);
//                ticket.setStatus(TicketStatus.PENDING);
//                releasingTickets.add(ticket);
//            }
//            return true;
//            //TODO LOG
//        }
//    }
//
//    @Override
//    public void run() {
//        Thread.currentThread().setName(getVendorId());
//        Thread.currentThread().setPriority(Config.HighPriority);
//
//        boolean IsActive = releasableTicketsToMainPool();
//        while (IsActive) {
//            try {
//                int capacityOfTicketPool;
//                capacityOfTicketPool = ticketpool.ticketPoolCapacityCheck();
//                ArrayList<Ticket> ticketsForRelease = new ArrayList<>();
//
//                if ((getUnReleasingTickets() >= ticketsPerRelease)) {
//                    if (ticketsPerRelease > capacityOfTicketPool) {
//                        addToTempListFromVendorList(capacityOfTicketPool, ticketsForRelease);
//                    } else {
//                        addToTempListFromVendorList(ticketsPerRelease, ticketsForRelease);
//                    }
//                } else {
//                    addToTempListFromVendorList(getUnReleasingTickets(), ticketsForRelease);
//                }
//
//                ticketpool.addTicket(this, ticketsForRelease);
//                IsActive = getVendorStatus();
//
//                Thread.sleep(frequency * 1000L);
//
//            } catch (InterruptedException e) {
//                System.out.println("Ticket release interrupted for Vendor: " + vendorId);
//                Thread.currentThread().interrupt();
//                break;
//
//            }
//        }
//
//        //add releasable tickets to TotalTickets
//
//
//        //      TODO logging the Amount of ticket added amount
//
//
//    }
}
