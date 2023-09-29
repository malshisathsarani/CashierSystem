package javapackage;

import javapackage.FoodQueue;


import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    //defining maximum customers per cashier
    public static final int MAX_CUSTOMERS_CASHIER1 = 2;
    public static final int MAX_CUSTOMERS_CASHIER2 = 3;
    public static final int MAX_CUSTOMERS_CASHIER3 = 5;
    // Initial stock of burgers
    static int burgerStock = 50;
   //price of a burger
    public static final int BURGER_PRICE = 650;
    // Lists of customer queues and waiting lis
    public static ArrayList<String> waitingList;

    public static ArrayList<String> cashier1;
    public static ArrayList<String> cashier2;
    public static ArrayList<String> cashier3;


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Initialize the array lists
        cashier1 = new ArrayList<>();
        cashier2 = new ArrayList<>();
        cashier3 = new ArrayList<>();
        waitingList = new ArrayList<>();

        //get user inputs
        String choice;
        do {
            displayMenu();
            choice = input.next();
            switch (choice) {
                case "100":
                case "VFQ":
                    //View all queue customers
                    FoodQueue.viewAllQueues(cashier1, cashier2, cashier3);
                    break;

                case "101":
                case "VEQ":
                    //View all empty queues
                    FoodQueue.viewAllEmptyQueues(cashier1, cashier2, cashier3);
                    break;

                case "102":
                case "ACQ":
                    //Add customers to queues
                    Customer.addCustomerToQueue(input, cashier1, cashier2, cashier3);
                    break;

                case "103":
                case "RCQ":
                    //Remove customers from queues
                    FoodQueue.removeCustomerFromQueue(input, cashier1, cashier2, cashier3);
                    break;

                case "104":
                case "PCQ":
                    //Temove served customers
                    FoodQueue.removeServedCustomer(cashier1, cashier2, cashier3);
                    break;

                case "105":
                case "VCS":
                    //View all customers sorted
                    FoodQueue.viewCustomersSorted(cashier1, cashier2, cashier3);
                    break;

                case "106":
                case "SPD":
                    //Store programme date in to a file
                    FoodQueue.storeProgramDataIntoFile(input);
                    break;

                case "107":
                case "LPD":
                    //Load programe data from file
                    FoodQueue.loadProgramDataFromFile();
                    break;

                case "108":
                case "STK":
                    //view remaining burger stock
                    FoodQueue.viewRemainingBurgersStock();
                    break;

                case "109":
                case "AFS":
                    //Add burgers to stock
                    FoodQueue.addBurgersToStock(input);
                    break;

                case "110":
                case "IFQ":
                    //Print income of each queues
                    FoodQueue.printIncomeOfEachQueue();
                    break;

                case "999":
                case "EXT":
                    //Exit the programe
                    FoodQueue.exitProgram();
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
            System.out.println();
        //Ignore the case sensitive
        } while (!choice.equalsIgnoreCase("999"));
    }
    //display the menu
    private static void displayMenu() {
        System.out.println("100 or VFQ: View all Queues");
        System.out.println("101 or VEQ: View all Empty Queues");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order (Do not use library sort routine)");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data from file");
        System.out.println("108 or STK: View Remaining burgers Stock");
        System.out.println("109 orAFS: Add burgers to Stock");
        System.out.println("110 or IFQ: Income of each queue");
        System.out.println("999 or EXT: Exit the Program");
        System.out.println("Enter your choice:");
    }
}
