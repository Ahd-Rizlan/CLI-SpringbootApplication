package com.ticketingSystem.TicketingSimulation.dto;

public class VendorDTO {
    private String vendorId;
    private int frequency;
    private int totalTicketsToRelease;
    private int ticketsPerRelease;

    public VendorDTO(String vendorId, int frequency, int totalTicketsToRelease, int ticketsPerRelease) {
        this.vendorId = vendorId;
        this.frequency = frequency;
        this.totalTicketsToRelease = totalTicketsToRelease;
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
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
}
