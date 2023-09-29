import java.util.Scanner;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


public class FoodCenter {
    private static int burgerStock;
    private static final int MAX_CUSTOMERS_CASHIER1 = 2;
    private static final int MAX_CUSTOMERS_CASHIER2 = 3;
    private static final int MAX_CUSTOMERS_CASHIER3 = 5;
    private static String[] cashier1;
    private static String[] cashier2;
    private static String[] cashier3;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        cashier1 = new String[MAX_CUSTOMERS_CASHIER1];
        cashier2 = new String[MAX_CUSTOMERS_CASHIER2];
        cashier3 = new String[MAX_CUSTOMERS_CASHIER3];

        burgerStock = 50;

        String choice;
        do {
            displayMenu();
            choice = input.next();
            switch (choice) {
                case "100":
                case "VFQ":
                    viewAllQueues(cashier1, cashier2, cashier3);
                    break;

                case "101":
                case "VEQ":
                    viewAllEmptyQueues(cashier1, cashier2, cashier3);
                    break;

                case "102":
                case "ACQ":
                    addCustomerToQueue(input, cashier1, cashier2, cashier3);
                    break;

                case "103":
                case "RCQ":
                    removeCustomerFromQueue(input, cashier1, cashier2, cashier3);
                    break;

                case "104":
                case "PCQ":
                    removeServedCustomer(cashier1, cashier2, cashier3);
                    break;

                case "105":
                case "VCS":
                    viewCustomersSorted(cashier1, cashier2, cashier3);
                    break;

                case "106":
                case "SPD":
                    StoreProgramDataIntoFile(input);
                    break;

                case "107":
                case "LPD":
                    loadProgramDataFromFile();
                    break;

                case "108":
                case "STK":
                    viewRemainingBurgersStock();
                    break;

                case "109":
                case "AFS":
                    addBurgersToStock(input);
                    break;

                case "999":
                case "EXT":
                    exitProgram();
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
            System.out.println();
        } while (!choice.equalsIgnoreCase("999"));
    }

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
        System.out.println("109 or AFS: Add burgers to Stock");
        System.out.println("999 or EXT: Exit the Program");
        System.out.println("Enter your choice:");
    }
    public static void viewAllQueues(String[] cashier1, String[] cashier2, String[] cashier3) {
        System.out.println("*******");
        System.out.println("* Cashiers *");
        System.out.println("*******");
        System.out.print("Cashier 1: ");
        printQueue(cashier1);
        System.out.print("Cashier 2: ");
        printQueue(cashier2);
        System.out.print("Cashier 3: ");
        printQueue(cashier3);
    }

    private static void printQueue(String[] queue) {
        for (String customer : queue) {
            if (customer == null) {
                System.out.print("X ");
            } else {
                System.out.print("O ");
            }
        }
        System.out.println();
    }


    public static void viewAllEmptyQueues(String[] cashier1, String[] cashier2, String[] cashier3) {
        System.out.println("*******");
        System.out.println("* Empty Queues *");
        System.out.println("*******");

        boolean isCashier1Empty = isEmptyQueue(cashier1);
        boolean isCashier2Empty = isEmptyQueue(cashier2);
        boolean isCashier3Empty = isEmptyQueue(cashier3);

        if (isCashier1Empty) {
            System.out.println("Cashier 1: Empty");
        } else {
            System.out.println("Cashier 1: Occupied");
        }

        if (isCashier2Empty) {
            System.out.println("Cashier 2: Empty");
        } else {
            System.out.println("Cashier 2: Occupied");
        }

        if (isCashier3Empty) {
            System.out.println("Cashier 3: Empty");
        } else {
            System.out.println("Cashier 3: Occupied");
        }
    }


    public static void addCustomerToQueue(Scanner input, String[] cashier1, String[] cashier2, String[] cashier3) {
        System.out.println("Enter the cashier number (1, 2, or 3):");
        int cashierNumber = input.nextInt();

        String[] selectedQueue;
        int maxCustomers;

        switch (cashierNumber) {
            case 1:
                selectedQueue = cashier1;
                maxCustomers = MAX_CUSTOMERS_CASHIER1;
                break;
            case 2:
                selectedQueue = cashier2;
                maxCustomers = MAX_CUSTOMERS_CASHIER2;
                break;
            case 3:
                selectedQueue = cashier3;
                maxCustomers = MAX_CUSTOMERS_CASHIER3;
                break;
            default:
                System.out.println("Invalid cashier number.");
                return;
        }

        if (isQueueFull(selectedQueue)) {
            System.out.println("The selected queue is full.");
            return;
        }

        System.out.println("Enter the customer name:");
        String customerName = input.next();

        selectedQueue[getEmptyIndex(selectedQueue)] = customerName;
        updateStock();

        System.out.println("Customer added to the queue.");
    }

    private static boolean isQueueFull(String[] queue) {
        return getEmptyIndex(queue) == -1;
    }

    private static int getEmptyIndex(String[] queue) {
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private static void updateStock() {
        burgerStock -= 5;
        if (burgerStock <= 10) {
            System.out.println("Warning: 10 Burgers only!!!");
        }
    }

    public static void removeCustomerFromQueue(Scanner input, String[] cashier1, String[] cashier2, String[] cashier3) {
        System.out.println("Enter the cashier number (1, 2, or 3):");
        int cashierNumber = input.nextInt();

        String[] selectedQueue;
        int maxCustomers;

        switch (cashierNumber) {
            case 1:
                selectedQueue = cashier1;
                maxCustomers = MAX_CUSTOMERS_CASHIER1;
                break;
            case 2:
                selectedQueue = cashier2;
                maxCustomers = MAX_CUSTOMERS_CASHIER2;
                break;
            case 3:
                selectedQueue = cashier3;
                maxCustomers = MAX_CUSTOMERS_CASHIER3;
                break;
            default:
                System.out.println("Invalid cashier number.");
                return;
        }

        if (isEmptyQueue(selectedQueue)) {
            System.out.println("The selected queue is empty.");
            return;
        }

        System.out.println("Enter the customer index to remove (0-" + (maxCustomers - 1) + "):");
        int customerIndex = input.nextInt();

        if (customerIndex < 0 || customerIndex >= maxCustomers) {
            System.out.println("Invalid customer index.");
            return;
        }

        if (selectedQueue[customerIndex] == null) {
            System.out.println("No customer at the specified index.");
        } else {
            System.out.println("Removed customer: " + selectedQueue[customerIndex]);
            selectedQueue[customerIndex] = null;
        }
    }

    private static boolean isEmptyQueue(String[] queue) {
        for (String customer : queue) {
            if (customer != null) {
                return false;
            }
        }
        return true;
    }

    public static void removeServedCustomer(String[] cashier1, String[] cashier2, String[] cashier3) {
        boolean removed = false;

        for (int i = 0; i < cashier1.length; i++) {
            if (cashier1[i] != null) {
                System.out.println("Removed served customer: " + cashier1[i]);
                cashier1[i] = null;
                removed = true;
                break;
            }
        }

        if (!removed) {
            for (int i = 0; i < cashier2.length; i++) {
                if (cashier2[i] != null) {
                    System.out.println("Removed served customer: " + cashier2[i]);
                    cashier2[i] = null;
                    removed = true;
                    break;
                }
            }
        }

        if (!removed) {
            for (int i = 0; i < cashier3.length; i++) {
                if (cashier3[i] != null) {
                    System.out.println("Removed served customer: " + cashier3[i]);
                    cashier3[i] = null;
                    break;
                }
            }
        }
    }

    public static void viewCustomersSorted(String[] cashier1, String[] cashier2, String[] cashier3) {
        String[] allCustomers = mergeQueues(cashier1, cashier2, cashier3);

        Arrays.sort(allCustomers); // Sort the array using Arrays.sort() method

        System.out.println("Customers Sorted in alphabetical order:");

        for (String customer : allCustomers) {
            if (customer != null) {
                System.out.println(customer);
            }
        }
    }

    private static String[] mergeQueues(String[] cashier1, String[] cashier2, String[] cashier3) {
        String[] allCustomers = new String[cashier1.length + cashier2.length + cashier3.length];
        int index = 0;

        for (String customer : cashier1) {
            if (customer != null) {
                allCustomers[index++] = customer;
            }
        }

        for (String customer : cashier2) {
            if (customer != null) {
                allCustomers[index++] = customer;
            }
        }

        for (String customer : cashier3) {
            if (customer != null) {
                allCustomers[index++] = customer;
            }
        }

        return allCustomers;
    }





    private static void StoreProgramDataIntoFile (Scanner input) {
        try {
            FileWriter writer = new FileWriter("text.txt");

            // Write the data for QUEUE1
            for (String customer : cashier1) {
                if (customer != null) {
                    writer.write("Queue1:" + customer + "\n");
                }
            }

            // Write the data for QUEUE2
            for (String customer : cashier2) {
                if (customer != null) {
                    writer.write("Queue2:" + customer + "\n");
                }
            }

            // Write the data for QUEUE3
            for (String customer : cashier3) {
                if (customer != null) {
                    writer.write("Queue3:" + customer + "\n");
                }
            }

            writer.close();
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing program data: " + e.getMessage());
        }
    }




    private static void loadProgramDataFromFile () {
        System.out.println("---Loading Data---");
        try {
            FileReader fileReader = new FileReader("text.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
            System.out.println("Data Loaded Successfully!");
        } catch (IOException e) {
            System.out.println("ERROR while loading data");
        }
    }



    public static void viewRemainingBurgersStock() {
        System.out.println("Remaining burgers in stock: " + burgerStock);
    }

    public static void addBurgersToStock(Scanner input) {
        System.out.println("Enter the number of burgers to add to stock:");
        int burgersToAdd = input.nextInt();
        burgerStock += burgersToAdd;
        System.out.println("Burgers added to stock.");
    }

    public static void exitProgram() {
        System.out.println("Exiting the program...");
    }
}