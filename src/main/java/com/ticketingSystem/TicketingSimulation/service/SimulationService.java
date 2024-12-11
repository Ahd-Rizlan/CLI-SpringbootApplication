package com.ticketingSystem.TicketingSimulation.service;

import com.ticketingSystem.TicketingSimulation.DTO.CustomerDTO;
import com.ticketingSystem.TicketingSimulation.DTO.VendorDTO;
import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.ticketingSystem.TicketingSimulation.WebSocketConfig.WebSocketHandler.logAndBrodcastMessage;
import static com.ticketingSystem.TicketingSimulation.constant.Config.TotalNumberOfVendors;
import static com.ticketingSystem.TicketingSimulation.constant.Config.TotalTicketsToRelease;

@Service
public class SimulationService {

    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<Vendor> vendorList = new ArrayList<>();
    private ArrayList<Thread> customerThreadList = new ArrayList<>();
    private ArrayList<Thread> vendorThreadList = new ArrayList<>();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean isPaused = false;

    @Autowired
    private ConfigurationService configurationService;

    @Bean
    @Lazy
    public Ticketpool ticketpool(ConfigurationService configurationService) {

        return new Ticketpool(configurationService.getConfiguration());
    }


    private Ticketpool ticketpool;


    //    --------------------------INITIATE THE SYSTEM--------------------------
    public void initiateConfiguration() {
        ticketpool = new Ticketpool(configurationService.getConfiguration());
    }

    //    --------------------------Create Vendors--------------------------

    public List<VendorDTO> createVendors(int NumberOFVendors, int TicketsPerRelease) {

        ticketpool = new Ticketpool(configurationService.getConfiguration());

        ArrayList<VendorDTO> createdVendorDTO = new ArrayList<>();

        Config.TotalNumberOfVendors += NumberOFVendors;

        int totalTicketsPerVendor = TotalTicketsToRelease / TotalNumberOfVendors;

        for (int i = 0; i < NumberOFVendors; i++) {
            Vendor vendor = new Vendor(TicketsPerRelease, ticketpool, configurationService.getConfiguration(), totalTicketsPerVendor);
            vendorList.add(vendor);
            createdVendorDTO.add(new VendorDTO(vendor));
            Thread vendorThread = new Thread(vendor);
            vendorThreadList.add(vendorThread);
            logAndBrodcastMessage("Vendor Created" + vendor.toString());

            //setting the maximum tickets to release
            for (Vendor vendorInList : vendorList) {
                vendorInList.setTotalTicketsToRelease(totalTicketsPerVendor);
            }
        }
        return createdVendorDTO;
    }


    //    -------------------------- Create Customers --------------------------
    public List<CustomerDTO> createCustomers(int NumberOfCustomers, int TicketsPerPurchase, boolean IsVip) {

        ticketpool = new Ticketpool(configurationService.getConfiguration());

        ArrayList<CustomerDTO> createdCustomersDTO = new ArrayList<>();

        for (int i = 0; i < NumberOfCustomers; i++) {
            if (IsVip) {
                Customer customer = new VipCustomer(TicketsPerPurchase, ticketpool, configurationService.getConfiguration());
                customerList.add(customer);// Adding Customer to All Customers List
                CustomerDTO customerDTO = new CustomerDTO(customer);
                createdCustomersDTO.add(customerDTO);

                Thread customerThread = new Thread(customer);
                customerThread.setPriority(Config.VipPriority);
                customerThreadList.add(customerThread);
                logAndBrodcastMessage("Vip Customer Created" + customer.toString());
            } else {
                Customer customer = new RegularCustomer(TicketsPerPurchase, ticketpool, configurationService.getConfiguration());
                customerList.add(customer);// Adding Customer to All Customers List
                CustomerDTO customerDTO = new CustomerDTO(customer);
                createdCustomersDTO.add(customerDTO);

                Thread customerThread = new Thread(customer);
                customerThread.setPriority(Config.LowPriority);
                customerThreadList.add(customerThread);
                logAndBrodcastMessage("Regular Customer Created" + customer.toString());
            }

        }
        return createdCustomersDTO;
    }

    //    -------------------------- Start Simulation --------------------------
    public String startSimulation() {
        for (Thread vendor : vendorThreadList) {
            vendor.start();
        }
        for (Thread customer : customerThreadList) {
            customer.start();
        }
        return "Simulation Started";
    }

    //    -------------------------- Stop Simulation --------------------------


    public String stopSimulation() {

        for (Thread vendor : vendorThreadList) {
            vendor.start();

        }

        lock.lock();
        try {
            isPaused = true;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        return "Simulation Paused";
    }

    public String resumeSimulation() {
        lock.lock();
        try {
            isPaused = false;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        return "Simulation Resumed";
    }

    public void checkPaused() throws InterruptedException {
        lock.lock();
        try {
            while (isPaused) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }


}