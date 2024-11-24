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

    public void writeGson(Configuration configuration) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Gson gson = builder.create();
            File file = new File(Config.configurationFile);
            Writer writer = new FileWriter(file);
            gson.toJson(configuration, writer);
            //LOG DATA ADDED TODO
            writer.close();

        } catch (IOException e) {
            System.out.println("Unable to save configuration file");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Configuration readGson() {
        try {
            File configFile = new File(Config.configurationFile);
            if (!configFile.exists()) {
                System.out.println("Configuration file does not exist");
                if (configFile.createNewFile()) {
                    System.out.println("New Configuration File Created");
                    setConfigurationFile();
                }
                return new Configuration();
            }

            Gson gson = new Gson();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Config.configurationFile));
            Configuration configuration = gson.fromJson(bufferedReader, Configuration.class);
            bufferedReader.close();
            return configuration;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("An error occurred while reading the configuration file");
            return new Configuration();
        }
    }

    public void setConfigurationFile() {
        //Check weather the file is there are not if there pre-assign values to valriables so we can update one record
        Scanner scanner = new Scanner(System.in);
        HandleFiles writeFiles = new HandleFiles();
        Validation validation = new Validation();
        Configuration configuration = new Configuration();

        boolean loop = true;
        while (loop) {
            System.out.println("-----------------------------------------------------------------");
            configuration.setTotalTickets(validation.getValidation(scanner, "Enter Total Ticket capacity for Event:"));
            // configuration.setMaxTicketCapacity(validation.getValidation(scanner, "Enter Maximum Pool Size : "));
            int totalTicket;
            do {
                totalTicket = validation.getValidation(scanner, "Enter Maximum Pool Size : ");
                configuration.setMaxTicketCapacity(totalTicket);
                writeFiles.writeOnGson(configuration);
            } while (!validation.validateTicketAmountforPool(totalTicket, configuration.getTotalTickets()));

            configuration.setTicketReleaseRate(validation.getValidation(scanner, "Enter The Release rate : "));
            configuration.setCustomerRetrievalRate(validation.getValidation(scanner, "Enter Purchase rate : "));
            writeFiles.writeOnGson(configuration);
            System.out.println("-----------------------------------------------------------------");
            break;
        }
    }

    public void getConfigurationFile() {
        //put this where Starting TODO

        File configFile = new File(Config.configurationFile);
        if (!configFile.exists()) {
            //can use with a custom message
            System.out.println("\n");
            setConfigurationFile();

        } else {
            Configuration configuration = readGson();
            //setting Values From the File
            configuration.setTicketReleaseRate(readGson().getTicketReleaseRate());
            configuration.setMaxTicketCapacity(readGson().getMaxTicketCapacity());
            configuration.setTotalTickets(readGson().getTotalTickets());
            configuration.setCustomerRetrievalRate(readGson().getCustomerRetrievalRate());


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



