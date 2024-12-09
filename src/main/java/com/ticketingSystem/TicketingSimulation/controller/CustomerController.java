package com.ticketingSystem.TicketingSimulation.controller;


import com.ticketingSystem.TicketingSimulation.dto.CustomerDTO;
import com.ticketingSystem.TicketingSimulation.entity.Customer;
import com.ticketingSystem.TicketingSimulation.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    Logger logger = (Logger) LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        logger.info("Fetching all Customers");
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        if (customers.isEmpty()) {
            logger.warn("No Customers Found");
            throw new EntityNotFoundException("No Customers Found");
        }
        for (Customer customer : customers) {
            int i =0;
            CustomerDTO customerDTO = new CustomerDTO(customer.getCustomerId(), customer.getIsVip(), customer.getTicketsPerPurchase(), customer.getRetrievalInterval());
            customerDTOList.add(customerDTO);
            logger.debug("{} Customer Found", i);
        }
        return new ResponseEntity<>(customerDTOList, HttpStatus.OK);
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String customerId) {
        logger.info("Fetching Customer by Id {}", customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            logger.error("Customer not found with {}", customerId);
            return new EntityNotFoundException("Customer not found");
        });
        CustomerDTO customerDTO = new CustomerDTO(customer.getCustomerId(), customer.getIsVip(), customer.getTicketsPerPurchase(), customer.getRetrievalInterval());
        logger.info("Successfully Fetched Customer by Id {}", customerId);

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);

    }

//    @PostMapping
//    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
//        Customer newCustomer = customerRepository.save(customer);
//        CustomerDTO customerDTO = new CustomerDTO(newCustomer.getCustomerId(), newCustomer.getIsVip(), newCustomer.getTicketsPerPurchase(), newCustomer.getRetrievalInterval());
//        logger.info("Fetching Customer by Id {}", customer.getCustomerId());
//        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
//    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
        Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(() -> {
            logger.error("Customer not found with {}", customerId);
            return new EntityNotFoundException("Customer not found");
        });
        existingCustomer.setRetrievalInterval(customer.getRetrievalInterval());
        existingCustomer.setTicketsPerPurchase(customer.getTicketsPerPurchase());
        existingCustomer.setVip(customer.getIsVip());
        customerRepository.save(existingCustomer);
        CustomerDTO customerDTO = new CustomerDTO(existingCustomer.getCustomerId(), existingCustomer.getIsVip(), existingCustomer.getTicketsPerPurchase(), existingCustomer.getRetrievalInterval());
        logger.info("Successfully Updated Customer by Id {}", customerId);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable String customerId) {
        Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        customerRepository.delete(existingCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCustomer() {
        customerRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
