package javapackage;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


import static javapackage.Main.*;


public class FoodQueue {

    // Method to view all queues
    public static void viewAllQueues(ArrayList<String> cashier1, ArrayList<String> cashier2, ArrayList<String> cashier3) {
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        System.out.print("Cashier 1: ");
        printQueueStatus(cashier1, 2);

        System.out.print("Cashier 2: ");
        printQueueStatus(cashier2, 3);

        System.out.print("Cashier 3: ");
        printQueueStatus(cashier3, 5);

        System.out.println();
        System.out.println("X - Occupied   O - Not Occupied");
    }

    //this method help to view all queues method to display changes
    private static void printQueueStatus(ArrayList<String> queue, int maxCustomers) {
        for (int i = 0; i < maxCustomers; i++) {
            if (i < queue.size() && queue.get(i) != null) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
        }
        System.out.println();
    }

    // Method to view all empty queues
    public static void viewAllEmptyQueues(ArrayList<String> cashier1, ArrayList<String> cashier2, ArrayList<String> cashier3) {
        System.out.println("*********** ");
        System.out.println("* Cashier *");
        System.out.println("*********** ");

        boolean isCashier1Empty = cashier1.isEmpty();
        boolean isCashier2Empty = cashier2.isEmpty();
        boolean isCashier3Empty = cashier3.isEmpty();

        System.out.printf("Cashier 1: %s%n", isCashier1Empty ? "Empty" : "Not Empty");
        System.out.printf("Cashier 2: %s%n", isCashier2Empty ? "Empty" : "Not Empty");
        System.out.printf("Cashier 3: %s%n", isCashier3Empty ? "Empty" : "Not Empty");
    }

    // Method to remove a customer from a specific position
    public static void removeCustomerFromQueue(Scanner input, ArrayList<String> cashier1, ArrayList<String> cashier2, ArrayList<String> cashier3) {
        System.out.println("Enter the cashier number (1, 2, or 3):");
        int cashierNumber = input.nextInt();

        ArrayList<String> selectedCashier = getCashierQueue(cashierNumber, cashier1, cashier2, cashier3);
        if (selectedCashier != null) {
            //if queue position is already empty
            if (selectedCashier.isEmpty()) {
                System.out.println("The queue of Cashier " + cashierNumber + " is already empty.");
            }

            else {
                System.out.println("Enter the position of the customer to remove:");
                int position = input.nextInt();

                //check the position and remove the customer
                if (position >= 0 && position <= selectedCashier.size()) {
                    String removedCustomer = selectedCashier.remove(position - 1);
                    System.out.println("Removed customer: " + removedCustomer);

                    //if someone in waiting list add him to the position of removed customer
                    if (!waitingList.isEmpty()) {
                        String nextCustomer = waitingList.remove(0);
                        selectedCashier.add(nextCustomer);
                        System.out.println("Customer from waiting list added to Cashier " + cashierNumber + " queue.");
                    }
                } else {
                    System.out.println("Invalid position. Please enter a valid position.");
                }
            }
        } else {
            System.out.println("Invalid cashier number. Please enter a valid cashier number.");
        }
    }

    //this method help to remove customer method to check the queue number
    private static ArrayList<String> getCashierQueue(int cashierNumber, ArrayList<String> cashier1, ArrayList<String> cashier2, ArrayList<String> cashier3) {
        if (cashierNumber == 1) {
            return cashier1;
        } else if (cashierNumber == 2) {
            return cashier2;
        } else if (cashierNumber == 3) {
            return cashier3;
        } else {
            return null;
        }
    }

    // Method to remove the served customer from any queue
    public static void removeServedCustomer(ArrayList<String> cashier1, ArrayList<String> cashier2, ArrayList<String> cashier3) {

        //here when check the all empty queues and if all are empty ,
        if (cashier1.isEmpty() && cashier2.isEmpty() && cashier3.isEmpty()) {
            System.out.println("No served customers to remove.");
            return;
        }

        // Remove the served customer from the selected cashier's queue
        ArrayList<String> selectedCashier = null;
        if (!cashier3.isEmpty()) {
            selectedCashier = cashier3;
        } else if (!cashier2.isEmpty()) {
            selectedCashier = cashier2;
        } else if (!cashier1.isEmpty()) {
            selectedCashier = cashier1;
        }

        if (selectedCashier != null) {
            String removedCustomer = selectedCashier.remove(0);
            int burgersRequired = getBurgersRequired(removedCustomer);
            int burgersToRemove = Math.min(burgerStock, burgersRequired);
            burgerStock -= burgersToRemove;

            System.out.println("Removed served customer: " + removedCustomer);
            System.out.println("Burgers stock reduced by: " + burgersToRemove);

            // Add the next customer from the waiting list
            if (!waitingList.isEmpty()) {
                String nextCustomer = waitingList.remove(0);
                selectedCashier.add(nextCustomer);
                System.out.println("Customer from waiting list added to Cashier " + selectedCashier + " queue.");
            }
        }
    }


    public static void viewCustomersSorted(ArrayList<String> cashier1, ArrayList<String> cashier2, ArrayList<String> cashier3) {

        // Combine all customers from the cashiers into one list
        ArrayList<String> allCustomers = new ArrayList<>();
        allCustomers.addAll(cashier1);
        allCustomers.addAll(cashier2);
        allCustomers.addAll(cashier3);

        if (allCustomers.isEmpty()) {
            System.out.println("No customers to display.");
        } else {
            // Bubble sort the customer names in ascending order
            for (int i = 0; i < allCustomers.size() - 1; i++) {
                for (int j = 0; j < allCustomers.size() - i - 1; j++) {
                    if (allCustomers.get(j).compareToIgnoreCase(allCustomers.get(j + 1)) > 0) {
                        String temp = allCustomers.get(j);
                        allCustomers.set(j, allCustomers.get(j + 1));
                        allCustomers.set(j + 1, temp);
                    }
                }
            }

            // Print the sorted customers
            System.out.println("Sorted Customers:");
            for (String customer : allCustomers) {
                System.out.println(customer);
            }
        }
    }
    public static void storeProgramDataIntoFile(Scanner input) {
        System.out.println("Enter the file name to store the program data:");
        String fileName = input.next();

        //use try option to fix errors
        // Write Cashier 1 queue data
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Cashier 1 Queue:\n");
            for (String customer : cashier1) {
                writer.write(customer + "\n");
            }

            // Write Cashier 2 queue data
            writer.write("\nCashier 2 Queue:\n");
            for (String customer : cashier2) {
                writer.write(customer + "\n");
            }

            // Write Cashier 3 queue data
            writer.write("\nCashier 3 Queue:\n");
            for (String customer : cashier3) {
                writer.write(customer + "\n");
            }

            System.out.println("Program data stored into the file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while storing program data: " + e.getMessage());
        }
    }

    public static void loadProgramDataFromFile() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the file name to load the program data:");
        String fileName = input.next();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String currentCashier = "";
            ArrayList<String> currentQueue = null;

            while ((line = reader.readLine()) != null) {

                // Determine the current cashier based on the line
                if (line.startsWith("Cashier")) {
                    currentCashier = line;
                    currentQueue = getCashierQueueFromName(currentCashier);
                } else if (currentQueue != null) {

                    // Add the line to the current cashier's queue
                    currentQueue.add(line);
                }
            }

            System.out.println("Program data loaded from the file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while loading program data: " + e.getMessage());
        }
    }

    //this is a help method for load programe method
    public static ArrayList<String> getCashierQueueFromName(String cashierName) {
        if (cashierName.contains("Cashier 1")) {
            return cashier1;
        } else if (cashierName.contains("Cashier 2")) {
            return cashier2;
        } else if (cashierName.contains("Cashier 3")) {
            return cashier3;
        } else {
            return null;
        }
    }

    public static void viewRemainingBurgersStock() {

        //print burger stock
        System.out.println("Remaining Burgers Stock: " + burgerStock);
    }

    public static void addBurgersToStock(Scanner input) {
        System.out.println("Enter the number of burgers to add:");
        int quantity = input.nextInt();

        if (quantity > 0) {

            // Increase the burger stock by the specified quantity
            burgerStock += quantity;
            System.out.println("Added " + quantity + " burgers to the stock.");
            System.out.println("Updated Burgers Stock: " + burgerStock);

        } else {

            // Display an error message for an invalid quantity
            System.out.println("Invalid quantity. Please enter a positive number.");
        }
    }


    public static void printIncomeOfEachQueue() {
        int income1 = calculateIncome(cashier1);
        int income2 = calculateIncome(cashier2);
        int income3 = calculateIncome(cashier3);

        System.out.println("Income of each queue:");
        System.out.println("Cashier 1: " + income1);
        System.out.println("Cashier 2: " + income2);
        System.out.println("Cashier 3: " + income3);
    }

    public static int calculateIncome(ArrayList<String> queue) {

        // Calculate the total number of burgers
        int totalBurgers = 0;
        for (String customer : queue) {
            int burgers = getBurgersRequired(customer);
            totalBurgers += burgers;
        }

        // Calculate the income by multiplying the total burgers by the burger price
        return totalBurgers * BURGER_PRICE;
    }

    public static int getBurgersRequired(String customer) {

        // Split the customer string to extract the burgers information
        String[] tokens = customer.split("-");
        String burgersInfo = tokens[1].trim();
        String[] burgersTokens = burgersInfo.split(":");

        // Extract and parse the number of burgers required
        return Integer.parseInt(burgersTokens[1].trim());
    }

    public static void exitProgram() {
        //programme exit
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}



