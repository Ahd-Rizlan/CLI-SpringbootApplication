package com.ticketingSystem.TicketingSimulation.controller;


import ch.qos.logback.classic.Logger;
import com.ticketingSystem.TicketingSimulation.DTO.ConfigurationDTO;
import com.ticketingSystem.TicketingSimulation.entity.Configuration;
import com.ticketingSystem.TicketingSimulation.repository.ConfigurationRepository;
import com.ticketingSystem.TicketingSimulation.service.ConfigurationService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationRepository configurationRepository;
    @Autowired
    private final ConfigurationService configurationService;

    Logger logger = (Logger) LoggerFactory.getLogger(ConfigurationController.class);

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping
    public ResponseEntity<ConfigurationDTO> getAllConfigurations() {
        List<Configuration> configurations = configurationRepository.findAll();
        if (configurations.isEmpty()) {
            throw new EntityNotFoundException("No Configurations Found");
        }
        Configuration configuration = configurationRepository.getReferenceById(1L);
        ConfigurationDTO configurationDTO = new ConfigurationDTO(configuration);

        logger.info("Retrieved - {}", configuration.toString());

        return new ResponseEntity<>(configurationDTO, HttpStatus.OK);
    }

    //----------------------------------------------------------------------------------
    // Create a new configuration
    //----------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<ConfigurationDTO> createOrUpdateConfiguration(@RequestBody Configuration configuration) {
        configurationRepository.deleteAll();
        configuration.setId(1L);
        Configuration newConfiguration = configurationRepository.save(configuration);
        ConfigurationDTO configurationDTO = new ConfigurationDTO(newConfiguration);
        logger.info("Created - " + configuration.toString());
        configurationService.saveConfig(configuration);

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
        return new ResponseEntity<>(configurationDTO, HttpStatus.OK);
    }
}