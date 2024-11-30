package com.ticketingSystem.TicketingSimulation.controller;

import com.ticketingSystem.TicketingSimulation.dto.VendorDTO;
import com.ticketingSystem.TicketingSimulation.model.Vendor;
import com.ticketingSystem.TicketingSimulation.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        if (vendors.isEmpty()) {
            throw new EntityNotFoundException("No Vendors Found");
        }
        List<VendorDTO> vendorDTOList = vendors.stream()
    .map(vendor -> new VendorDTO(vendor.getVendorId(), vendor.getFrequency(), vendor.getTotalTicketsToRelease(), vendor.getTicketsPerRelease()))
    .collect(Collectors.toList());
        return new ResponseEntity<>(vendorDTOList, HttpStatus.OK);
    }
    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable String vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
        VendorDTO vendorDTO = new VendorDTO(vendor.getVendorId(), vendor.getFrequency(), vendor.getTotalTicketsToRelease(), vendor.getTicketsPerRelease());
        return new ResponseEntity<>(vendorDTO, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@RequestBody Vendor vendor) {
        Vendor newVendor = vendorRepository.save(vendor);
        VendorDTO vendorDTO = new VendorDTO(newVendor.getVendorId(), newVendor.getFrequency(), newVendor.getTotalTicketsToRelease(), newVendor.getTicketsPerRelease());
        return new ResponseEntity<>(vendorDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable String vendorId, @RequestBody Vendor vendor) {
        Vendor existingVendor = vendorRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
        existingVendor.setFrequency(vendor.getFrequency());
        existingVendor.setTotalTicketsToRelease(vendor.getTotalTicketsToRelease());
        existingVendor.setTicketsPerRelease(vendor.getTicketsPerRelease());
        Vendor updatedVendor = vendorRepository.save(existingVendor);
        VendorDTO vendorDTO = new VendorDTO(updatedVendor.getVendorId(), updatedVendor.getFrequency(), updatedVendor.getTotalTicketsToRelease(), updatedVendor.getTicketsPerRelease());
        return new ResponseEntity<>(vendorDTO, HttpStatus.OK);
    }
    @DeleteMapping("/{vendorId}")
    public ResponseEntity<Void> deleteVendorById(@PathVariable String vendorId) {
        Vendor existingVendor = vendorRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
        vendorRepository.delete(existingVendor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteVendor() {
        vendorRepository.deleteAll();
        return new ResponseEntity<>("Vendors Deleted",HttpStatus.OK);
    }









}
