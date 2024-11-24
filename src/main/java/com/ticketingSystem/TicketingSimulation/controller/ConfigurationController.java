package com.ticketingSystem.TicketingSimulation.controller;

import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.model.Configuration;
import com.ticketingSystem.TicketingSimulation.service.ConfigurationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class ConfigurationController {


    private ConfigurationService configurationService ;
    Configuration configuration = configurationService.readGson();
    //get all the configuration


    @GetMapping("/configurations")
    public Configuration getAllConfigurations() {
    return configuration;
    }
}

        //update the configuration


        //delete the configuration


        //config file and Database




