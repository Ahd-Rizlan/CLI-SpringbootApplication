package com.ticketingSystem.TicketingSimulation.controller;

import ch.qos.logback.classic.Logger;
import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.entity.Configuration;
import com.ticketingSystem.TicketingSimulation.entity.Customer;
import com.ticketingSystem.TicketingSimulation.entity.Vendor;
import com.ticketingSystem.TicketingSimulation.entity.Ticketpool;
import com.ticketingSystem.TicketingSimulation.repository.CustomerRepository;
import com.ticketingSystem.TicketingSimulation.repository.VendorRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SimulationController {

    @Autowired
    private ConfigurationController configurationController;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private CustomerRepository customerRepository;


    Logger logger = (Logger) LoggerFactory.getLogger(SimulationController.class);
    private final Ticketpool ticketpool = new Ticketpool(100, 100);
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<Vendor> vendorTList = new ArrayList<>();
    private ArrayList<Thread> customerThreadList = new ArrayList<>();
    private ArrayList<Thread> vendorThreadList = new ArrayList<>();
    private volatile boolean isSimulationRunning = true;

    @PostMapping("/createVendors")
    public ResponseEntity<List> createVendors(@RequestParam int NumberOFVendors, @RequestParam int TicketsPerRelease, @RequestParam int FrequencyInSeconds) {
        ArrayList<Vendor> createdVendor = new ArrayList<>();

        Config.TotalNumberOfVendors += NumberOFVendors;
        for (int i = 0; i < NumberOFVendors; i++) {
            Vendor vendor = new Vendor(TicketsPerRelease, FrequencyInSeconds, ticketpool, configurationController.getConfigurations());
            vendorTList.add(vendor);
            createdVendor.add(vendor);
            Thread vendorThread = new Thread(vendor);
            System.out.println("Vendor Created" + vendor.toString());
            logger.info("Vendor Created" + vendor.toString());
            vendorRepository.save(vendor);
            vendorThreadList.add(vendorThread);
        }
        return new ResponseEntity<>(createdVendor, HttpStatus.OK);
    }

    @PostMapping("/createCustomers")
    public ResponseEntity<List> createCustomers(@RequestParam int NumberOFCustomers, @RequestParam int TicketsPerPurchase, @RequestParam int ReterivalInterval, @RequestParam boolean isVip) {
        ArrayList<Customer> createdCustomer = new ArrayList<>();
        for (int i = 0; i < NumberOFCustomers; i++) {
            Customer customer = new Customer(isVip, TicketsPerPurchase, ReterivalInterval, ticketpool, configurationController.getConfigurations());
            customerList.add(customer);// Adding Customer to All Customers List
            createdCustomer.add(customer);// Adding Customer to Created Customers List
            Thread customerThread = new Thread(customer);
            System.out.println("Customer Created" + customer.toString());
            logger.info("customer Created" + customer.toString());
            customerRepository.save(customer);
            customerThreadList.add(customerThread);
        }
        return new ResponseEntity<>(createdCustomer, HttpStatus.OK);
    }

    @PostMapping("/startSimulation")
    public ResponseEntity<String> startSimulation() {
        isSimulationRunning = true;
        for (Thread vendor : vendorThreadList) {
            vendor.start();
        }
        for (Thread customer : customerThreadList) {
            customer.start();
        }
        return new ResponseEntity<>("Simulation Started", HttpStatus.OK);
    }

    @PostMapping("/stopSimulation")
    public ResponseEntity<String> stopSimulation() {
        for (Thread vendorThread : vendorThreadList) {
            vendorThread.interrupt();
        }

        for (Thread customerThread : customerThreadList) {
            customerThread.interrupt();
        }

        vendorThreadList.clear();
        customerThreadList.clear();

        return new ResponseEntity<>("Simulation Stopped", HttpStatus.OK);
    }

    public boolean isSimulationRunning() {
        return isSimulationRunning;
    }
}