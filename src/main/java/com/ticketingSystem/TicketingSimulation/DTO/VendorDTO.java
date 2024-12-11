package com.ticketingSystem.TicketingSimulation.DTO;

import com.ticketingSystem.TicketingSimulation.entity.Vendor;

public class VendorDTO {
    String vendorId;
    private int totalTicketsToRelease;
    private int ticketsPerRelease;
    private final int frequency;

    public VendorDTO(Vendor vendor) {
        this.vendorId = vendor.getVendorId();
        this.totalTicketsToRelease = vendor.getTotalTicketsToRelease();
        this.ticketsPerRelease = vendor.getTicketsPerRelease();
        this.frequency = vendor.getFrequency();
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
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

    public int getFrequency() {
        return frequency;
    }
}
