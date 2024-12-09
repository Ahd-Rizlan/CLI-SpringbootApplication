package com.ticketingSystem.TicketingSimulation.controller;

import ch.qos.logback.classic.Logger;
import com.ticketingSystem.TicketingSimulation.dto.VendorDTO;
import com.ticketingSystem.TicketingSimulation.entity.Vendor;
import com.ticketingSystem.TicketingSimulation.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    Logger logger = (Logger) LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        logger.info("Fetching all Vendors");
        List<VendorDTO> vendorDTOList = new ArrayList<>();
        if (vendors.isEmpty()) {
            logger.warn("No Vendors Found");
            throw new EntityNotFoundException("No Vendors Found");
        }
        for (Vendor vendor : vendors) {
            int i =0;
            VendorDTO vendorDTO = new VendorDTO(vendor.getVendorId(), vendor.getFrequency(), vendor.getTotalTicketsToRelease(), vendor.getTicketsPerRelease());
            vendorDTOList.add(vendorDTO);
            logger.debug(i +" Vendor Found");
        }
        return new ResponseEntity<>(vendorDTOList, HttpStatus.OK);
    }
    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable String vendorId) {
        logger.info("Fetching Vendor by Id {}",vendorId);
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> {
            logger.error("Vendor not found with {}",vendorId);
            return new EntityNotFoundException("Vendor not found");
        });
        VendorDTO vendorDTO = new VendorDTO(vendor.getVendorId(), vendor.getFrequency(), vendor.getTotalTicketsToRelease(), vendor.getTicketsPerRelease());
        logger.info("Successfully Fetched Vendor by Id {}",vendorId);

        return new ResponseEntity<>(vendorDTO, HttpStatus.OK);
    }
//    @PostMapping
//    public ResponseEntity<VendorDTO> createVendor(@RequestBody Vendor vendor) {
//        Vendor newVendor = vendorRepository.save(vendor);
//        VendorDTO vendorDTO = new VendorDTO(newVendor.getVendorId(), newVendor.getFrequency(), newVendor.getTotalTicketsToRelease(), newVendor.getTicketsPerRelease());
//        logger.info("Fetching Vendor by Id {}",vendor.getVendorId());
//        return new ResponseEntity<>(vendorDTO, HttpStatus.CREATED);
//    }
    @PutMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable String vendorId, @RequestBody Vendor vendor) {
        Vendor existingVendor = vendorRepository.findById(vendorId).orElseThrow(() -> {
            logger.error("Vendor not found with {}",vendorId);
            return new EntityNotFoundException("Vendor not found");

        });
        existingVendor.setFrequency(vendor.getFrequency());
        existingVendor.setTotalTicketsToRelease(vendor.getTotalTicketsToRelease());
        existingVendor.setTicketsPerRelease(vendor.getTicketsPerRelease());
        Vendor updatedVendor = vendorRepository.save(existingVendor);
        VendorDTO vendorDTO = new VendorDTO(updatedVendor.getVendorId(), updatedVendor.getFrequency(), updatedVendor.getTotalTicketsToRelease(), updatedVendor.getTicketsPerRelease());
        return new ResponseEntity<>(vendorDTO, HttpStatus.OK);
    }
    @DeleteMapping("/{vendorId}")
    public ResponseEntity<String> deleteVendorById(@PathVariable String vendorId) {
        Vendor existingVendor = vendorRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
        vendorRepository.delete(existingVendor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteVendor() {
        vendorRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }









}
