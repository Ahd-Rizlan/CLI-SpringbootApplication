package com.ticketingSystem.TicketingSimulation.controller;

import ch.qos.logback.classic.Logger;
import com.ticketingSystem.TicketingSimulation.DTO.CustomerDTO;
import com.ticketingSystem.TicketingSimulation.DTO.VendorDTO;
import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.entity.Configuration;
import com.ticketingSystem.TicketingSimulation.entity.Customer;
import com.ticketingSystem.TicketingSimulation.entity.Vendor;
import com.ticketingSystem.TicketingSimulation.entity.Ticketpool;
import com.ticketingSystem.TicketingSimulation.service.ConfigurationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ticketingSystem.TicketingSimulation.constant.Config.TotalNumberOfVendors;
import static com.ticketingSystem.TicketingSimulation.constant.Config.TotalTicketsToRelease;

@RestController
@RequestMapping("/api")
public class SimulationController {

    @Autowired
    private ConfigurationService configurationService;


    Logger logger = (Logger) LoggerFactory.getLogger(SimulationController.class);
    
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<Vendor> vendorList = new ArrayList<>();
    private ArrayList<Thread> customerThreadList = new ArrayList<>();
    private ArrayList<Thread> vendorThreadList = new ArrayList<>();


    @Bean
    @Lazy
    public Ticketpool ticketpool(ConfigurationService configurationService) {
        return new Ticketpool(configurationService.getConfiguration());
    }
    private  Ticketpool ticketpool ;



    @PostMapping("/createVendors")
    public ResponseEntity<List> createVendors(@RequestParam int NumberOFVendors, @RequestParam int TicketsPerRelease) {
        ticketpool = new Ticketpool(configurationService.getConfiguration());
        ArrayList<Vendor> createdVendor = new ArrayList<>();
        ArrayList<VendorDTO> createdVendorDTO = new ArrayList<>();
        Config.TotalNumberOfVendors += NumberOFVendors;
        int totalTicketsPerVendor = TotalTicketsToRelease / TotalNumberOfVendors;


        for (int i = 0; i < NumberOFVendors; i++) {
            Vendor vendor = new Vendor(TicketsPerRelease, ticketpool,configurationService.getConfiguration(),totalTicketsPerVendor);
           // Vendor vendor = new Vendor(TicketsPerRelease, ticketpool,configurationService.getConfiguration());
            VendorDTO vendorDTO = new VendorDTO(vendor);
            createdVendorDTO.add(vendorDTO);

            vendorList.add(vendor);
            createdVendor.add(vendor);
            Thread vendorThread = new Thread(vendor);
            System.out.println("Vendor Created " + vendor.toString());

            //logger.info("Vendor Created{}", vendor.toString());

            vendorThreadList.add(vendorThread);
        }
        for (Vendor vendor : vendorList) {
            vendor.setTotalTicketsToRelease(totalTicketsPerVendor);
        }

        return new ResponseEntity<>(createdVendorDTO, HttpStatus.OK);
    }

    @PostMapping("/createCustomers")
    public ResponseEntity<List> createCustomers(@RequestParam int NumberOFCustomers, @RequestParam int TicketsPerPurchase, @RequestParam boolean isVip) {
        ArrayList<Customer> createdCustomer = new ArrayList<>();
        ArrayList<CustomerDTO> createdCustomerDTO = new ArrayList<>();

        for (int i = 0; i < NumberOFCustomers; i++) {
            Customer customer = new Customer(isVip, TicketsPerPurchase,ticketpool, configurationService.getConfiguration());
            customerList.add(customer);// Adding Customer to All Customers List
            createdCustomer.add(customer);// Adding Customer to Created Customers List
            CustomerDTO customerDTO = new CustomerDTO(customer);
            createdCustomerDTO.add(customerDTO);

            Thread customerThread = new Thread(customer);
            System.out.println("Customer Created" + customer.toString());
           // logger.info("customer Created" + customer.toString());

            customerThreadList.add(customerThread);
        }
        return new ResponseEntity<>(createdCustomerDTO, HttpStatus.OK);
    }

    @PostMapping("/startSimulation")
    public ResponseEntity<String> startSimulation() {


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


}