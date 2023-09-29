package javapackage;

import javapackage.Main;

import java.util.Scanner;
import java.util.ArrayList;

import static javapackage.Main.*;

public class Customer {
    // Method to add a customer to a queue
    public static void addCustomerToQueue(Scanner input, ArrayList<String> cashier1, ArrayList<String> cashier2, ArrayList<String> cashier3) {
        //ask first name
        System.out.println("Enter first name:");
        String firstName = input.next();

        //ask last name
        System.out.println("Enter last name:");
        String lastName = input.next();

        //ask quantity of burgers to buy
        System.out.println("Enter the number of burgers required:");
        int burgersRequired = input.nextInt();

        //display to customer
        String customerEntry = firstName + " " + lastName + " - Burgers: " + burgersRequired;
        System.out.println("Customer added Succesfully ! ");

        if (burgerStock >= burgersRequired) {

            // check if there is space in the cashier1 queue
            if (cashier1.size() < MAX_CUSTOMERS_CASHIER1) {
                cashier1.add(customerEntry);

            //check if there is space in the cashier2 queue
            } else if (cashier2.size() < MAX_CUSTOMERS_CASHIER2) {
                cashier2.add(customerEntry);

            //check if there is space in the cashier3 queue
            } else if (cashier3.size() < MAX_CUSTOMERS_CASHIER3) {
                cashier3.add(customerEntry);
            } else {
                //when spaces are unavailable, add customer to waiting list queue
                waitingList.add(customerEntry);
                System.out.println("All queues are full. Added customer to the waiting list.");
            }

            //reduce the burger stock
            burgerStock -= burgersRequired;
            System.out.println("Burgers stock reduced by: " + burgersRequired);

        } else {
            System.out.println("Insufficient burgers in stock to fulfill the order.");
        }
    }


}
