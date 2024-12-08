package com.ticketingSystem.TicketingSimulation.controller;

import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.entity.Configuration;
import com.ticketingSystem.TicketingSimulation.entity.Customer;
import com.ticketingSystem.TicketingSimulation.entity.Vendor;
import com.ticketingSystem.TicketingSimulation.entity.Ticketpool;
import com.ticketingSystem.TicketingSimulation.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class SimulationController {

    @Autowired
    private ConfigurationController configurationController;

    private final Ticketpool ticketpool = new Ticketpool(configurationController.getConfigurations());
    private ArrayList<Thread> customerThreadList = new ArrayList<>();
    private ArrayList<Thread> vendorThreadList = new ArrayList<>();


    @PostMapping("/createVendors")
    public ResponseEntity<String> createVendors(@RequestParam int NumberOFVendors ,@RequestParam int TicketsPerRelease, @RequestParam int FrequencyInSeconds) {
        Config.TotalNumberOfVendors += NumberOFVendors;
        for (int i = 0; i < NumberOFVendors; i++) {
            Thread vendor = new Thread(new Vendor(TicketsPerRelease, FrequencyInSeconds,ticketpool,configurationController.getConfigurations()));
            vendorThreadList.add(vendor);

        }
        return new ResponseEntity<>(NumberOFVendors+ " Vendors Created", HttpStatus.OK);
    }

    @PostMapping("/createCustomers")
    public ResponseEntity<String> createCustomers(@RequestParam int NumberOFCustomers , @RequestParam int TicketsPerPurchase, @RequestParam int ReterivalInterval , @RequestParam boolean isVip) {
        for (int i = 0; i < ReterivalInterval; i++) {
            Thread customer = new Thread(new Customer(isVip, TicketsPerPurchase,ReterivalInterval,ticketpool,configurationController.getConfigurations()));
            customerThreadList.add(customer);

        }
        return new ResponseEntity<>(NumberOFCustomers+ " Customers Created", HttpStatus.OK);
    }
}
