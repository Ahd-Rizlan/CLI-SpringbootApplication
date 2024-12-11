package com.ticketingSystem.TicketingSimulation.controller;

import ch.qos.logback.classic.Logger;
import com.ticketingSystem.TicketingSimulation.DTO.CustomerDTO;
import com.ticketingSystem.TicketingSimulation.DTO.VendorDTO;
import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.entity.*;
import com.ticketingSystem.TicketingSimulation.service.ConfigurationService;
import com.ticketingSystem.TicketingSimulation.service.SimulationService;
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
@CrossOrigin
public class SimulationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private SimulationService simulationService;
//
//
//    Logger logger = (Logger) LoggerFactory.getLogger(SimulationController.class);
//
//    private ArrayList<Customer> customerList = new ArrayList<>();
//    private ArrayList<Vendor> vendorList = new ArrayList<>();
//    private ArrayList<Thread> customerThreadList = new ArrayList<>();
//    private ArrayList<Thread> vendorThreadList = new ArrayList<>();


//    @Bean
//    @Lazy
//    public Ticketpool ticketpool(ConfigurationService configurationService) {
//        return new Ticketpool(configurationService.getConfiguration());
//    }
//    private  Ticketpool ticketpool ;

    @GetMapping()
    public ResponseEntity<String> InitiateConfiguration(){
        simulationService.initiateConfiguration();
        return new ResponseEntity<>("Configuration Initiated", HttpStatus.OK);
    }

    @PostMapping("/createVendors")
    public ResponseEntity<List> createVendors(@RequestParam int NumberOFVendors, @RequestParam int TicketsPerRelease) {
//        //ticketpool = new Ticketpool(configurationService.getConfiguration());
//        ArrayList<Vendor> createdVendor = new ArrayList<>();
//        ArrayList<VendorDTO> createdVendorDTO = new ArrayList<>();
//        Config.TotalNumberOfVendors += NumberOFVendors;
//        int totalTicketsPerVendor = TotalTicketsToRelease / TotalNumberOfVendors;
//
//
//        for (int i = 0; i < NumberOFVendors; i++) {
//            Vendor vendor = new Vendor(TicketsPerRelease, ticketpool,configurationService.getConfiguration(),totalTicketsPerVendor);
//           // Vendor vendor = new Vendor(TicketsPerRelease, ticketpool,configurationService.getConfiguration());
//            VendorDTO vendorDTO = new VendorDTO(vendor);
//            createdVendorDTO.add(vendorDTO);
//
//            vendorList.add(vendor);
//            createdVendor.add(vendor);
//            Thread vendorThread = new Thread(vendor);
//            System.out.println("Vendor Created " + vendor.toString());
//
//            //logger.info("Vendor Created{}", vendor.toString());
//
//            vendorThreadList.add(vendorThread);
//        }
//        for (Vendor vendor : vendorList) {
//            vendor.setTotalTicketsToRelease(totalTicketsPerVendor);
//        }
        List<VendorDTO>createdVendorDTO ;

        createdVendorDTO = simulationService.createVendors(NumberOFVendors, TicketsPerRelease);

        return new ResponseEntity<>(createdVendorDTO, HttpStatus.OK);
    }

    @PostMapping("/createCustomers")
    public ResponseEntity<List> createCustomers(@RequestParam int NumberOFCustomers, @RequestParam int TicketsPerPurchase, @RequestParam boolean isVip) {
        List<CustomerDTO>createdCustomerDTO;
        createdCustomerDTO= simulationService.createCustomers(NumberOFCustomers, TicketsPerPurchase, isVip);
        return new ResponseEntity<>(createdCustomerDTO, HttpStatus.OK);
    }

    @PostMapping("/startSimulation")
    public ResponseEntity<String> startSimulation() {
        simulationService.startSimulation();
        return new ResponseEntity<>("Simulation Started", HttpStatus.OK);
    }

    @PostMapping("/resumeSimulation")
    public ResponseEntity<String> resumeSimulation() {
        simulationService.resumeSimulation();
        return new ResponseEntity<>("Simulation Stopped", HttpStatus.OK);
    }

    @PostMapping("/stopSimulation")
    public ResponseEntity<String> stopSimulation() {
        simulationService.stopSimulation();
        return new ResponseEntity<>("Simulation Stopped", HttpStatus.OK);
    }


}