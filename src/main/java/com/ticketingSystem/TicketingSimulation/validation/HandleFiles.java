package com.ticketingSystem.TicketingSimulation.validation;

import com.ticketingSystem.TicketingSimulation.model.Configuration;
import com.ticketingSystem.TicketingSimulation.service.ConfigurationService;;

public class HandleFiles {

    public void writeOnGson(Configuration configuration) {
        com.ticketingSystem.TicketingSimulation.service.ConfigurationService configurationService = new ConfigurationService();
        configurationService.saveConfig(configuration);
    }

//    public Integer readOnGson() {
//        ConfigurationService configurationService = new ConfigurationService();
//
//        try {
//            configurationService.readGson();
//
//        } catch (IOException e) {
//            System.out.println("Unexpected issue occurred while reading the Gson file ");
//            e.printStackTrace();
//        }
//        return
//    }
}
