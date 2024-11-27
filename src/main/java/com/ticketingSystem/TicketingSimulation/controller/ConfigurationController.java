package com.ticketingSystem.TicketingSimulation.controller;


import com.ticketingSystem.TicketingSimulation.dto.ConfigurationDTO;
import com.ticketingSystem.TicketingSimulation.model.Configuration;
import com.ticketingSystem.TicketingSimulation.repository.ConfigurationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @GetMapping
    public ResponseEntity<ConfigurationDTO> getAllConfigurations() {
        List<Configuration> configurations = configurationRepository.findAll();
        if (configurations.isEmpty()) {
            throw new  EntityNotFoundException("No Configurations Found");
        }
        Configuration configuration = configurationRepository.getReferenceById(1L);
        ConfigurationDTO configurationDTO = new ConfigurationDTO(configuration);

        return new ResponseEntity<>(configurationDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConfigurationDTO> createOrUpdateConfiguration(@RequestBody Configuration configuration) {
        configurationRepository.deleteAll();
        configuration.setId(1L);
        Configuration newConfiguration = configurationRepository.save(configuration);
        ConfigurationDTO configurationDTO = new ConfigurationDTO(newConfiguration);
        return new ResponseEntity<>(configurationDTO, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ConfigurationDTO> updateConfiguration(@RequestBody Configuration updatedConfiguration) {
        long id = 1L;
        Configuration configuration = configurationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Configuration not found"));
        configuration.setMaxTicketCapacity(updatedConfiguration.getMaxTicketCapacity());
        configuration.setTotalTickets(updatedConfiguration.getTotalTickets());
        configuration.setTicketReleaseRate(updatedConfiguration.getTicketReleaseRate());
        configuration.setCustomerRetrievalRate(updatedConfiguration.getCustomerRetrievalRate());
        Configuration newConfiguration = configurationRepository.save(configuration);
        ConfigurationDTO configurationDTO = new ConfigurationDTO(newConfiguration);
        return new ResponseEntity<>(configurationDTO,HttpStatus.OK);
    }
    @DeleteMapping()
    public String deleteConfiguration() {
        configurationRepository.deleteAll();
        return "Configuration deleted successfully!";
    }


}

        //update the configuration


        //delete the configuration


        //config file and Database




