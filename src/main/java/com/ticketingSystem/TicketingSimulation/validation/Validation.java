package com.ticketingSystem.TicketingSimulation.validation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validation {


    public int getValidation(Scanner input, String prompt) {
        int value;
        while (true) {
            System.out.println(prompt);
            try {
                value = Integer.parseInt(input.nextLine());
                if (value < 0) {
                    System.out.println("Please enter a positive number");
                } else {
                    return value;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Invalid input, Enter Numbers only ");
            }
        }


    }

    public boolean getBoolean(Scanner input, String prompt) {
//boolean value = false;
        String response;

        do {
            System.out.println(prompt);
            response = input.nextLine();
            if (response.equalsIgnoreCase("YES") || response.equalsIgnoreCase("Y")) {
                System.out.println("Vip");

                return true;
                // break;
            }
            if (response.equalsIgnoreCase("NO") || response.equalsIgnoreCase("N")) {
                System.out.println("Vcdip");

                return false;
                //  break;
            }
            System.out.println("Enter YES or NO");
        } while (!(response.equalsIgnoreCase("YES")) || response.equalsIgnoreCase("NO") || response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N"));
        return true;
    }

    public boolean validateTicketAmountforPool(int totalTickets, int maxTicketCapacity) {
        if (maxTicketCapacity == 0) {
            System.out.println("Add Max Ticket Capacity and then Add Total Tickets");
            System.out.println();
            return true;
        } else if (totalTickets > maxTicketCapacity) {
            System.out.println("Total Tickets exceeds max Capacity");
            System.out.println("Please input a Lesser Value than " + maxTicketCapacity);
            return false;
        }

        return true;
    }

}
