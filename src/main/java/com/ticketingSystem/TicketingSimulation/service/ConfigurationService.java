package com.ticketingSystem.TicketingSimulation.service;

import com.ticketingSystem.TicketingSimulation.model.Configuration;
import com.ticketingSystem.TicketingSimulation.constant.Config;
import com.ticketingSystem.TicketingSimulation.validation.HandleFiles;
import com.ticketingSystem.TicketingSimulation.validation.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

public class ConfigurationService {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveConfig(Configuration configuration) {
        try(Writer writer = new FileWriter(Config.configurationFile);) {
            //Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Gson gson = builder.create();
           // File file = new File(Config.configurationFile);
           // Writer writer = new FileWriter(file);
            gson.toJson(configuration, writer);
            //LOG DATA ADDED TODO
            writer.close();
        } catch (IOException e) {
            System.out.println("Unable to save configuration file");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Configuration readConfiguration() {
        File configFile = new File(Config.configurationFile);

            if (!configFile.exists()) {
                System.out.println("Configuration file does not exist");
                Configuration defaultConfig = defaultConfiguration();
                saveConfig(defaultConfig);
                return defaultConfig;
            }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Config.configurationFile))){
            Configuration configuration = gson.fromJson(bufferedReader, Configuration.class);
            bufferedReader.close();
            return configuration;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("An error occurred while reading the configuration file");
            return new Configuration();
        }
    }

    private Configuration defaultConfiguration() {
        Configuration defaultConfig = new Configuration();
        defaultConfig.setTotalTickets(0);
        defaultConfig.setMaxTicketCapacity(0);
        defaultConfig.setTicketReleaseRate(0);
        defaultConfig.setCustomerRetrievalRate(0);
        return defaultConfig;
    }


    public void createConfigFile( Configuration newConfiguration) {
        Configuration readConfig = readConfiguration();
        readConfig.setTotalTickets(newConfiguration.getTotalTickets());
        readConfig.setMaxTicketCapacity(newConfiguration.getMaxTicketCapacity());
        readConfig.setTicketReleaseRate(newConfiguration.getTicketReleaseRate());
        readConfig.setCustomerRetrievalRate(newConfiguration.getCustomerRetrievalRate());
        saveConfig(readConfig);

    }
    public void deleteConfigFile() {
        File configFile = new File(Config.configurationFile);
        if (configFile.delete()) {
            System.out.println("Configuration file deleted successfully");
        } else {
            System.out.println("Failed to delete the configuration file");
        }
    }
    public void printConfigFile(Configuration configuration) {
        System.out.println("\n");
        System.out.println("Total Tickets : " + configuration.getTotalTickets());
        System.out.println("Customer Retrieval rate : " + configuration.getCustomerRetrievalRate());
        System.out.println("Max TicketPool Capacity : " + configuration.getMaxTicketCapacity());
        System.out.println("Ticket Relase rate : " + configuration.getTicketReleaseRate());
        System.out.println("\n");

    }
}



