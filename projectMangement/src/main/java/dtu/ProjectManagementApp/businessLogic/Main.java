package dtu.ProjectManagementApp.businessLogic;

import java.text.ParseException;
import java.util.Scanner;

public class Main {
    
 public static void main(String[] args) throws ParseException {
    SystemStorage systemStorage = new SystemStorage(); // Create an instance of SystemStorage
    BLController blController = new BLController(systemStorage);
    UIConsole uiConsole = new UIConsole(blController);

    Scanner sc = new Scanner(System.in); // Create a scanner for user input
    System.out.println("Welcome to the Project Management System!");

    systemStorage.initiateTestUsers(blController); // JUST FOR TESTING PURPOSES, DELETE LATER
    System.out.println("ID: employee, employee2"); // JUST FOR TESTING PURPOSES, DELETE LATER

     // Login process
     uiConsole.displayLogin(sc);   // test ID: admin, manager, employee
     //uiConsole.start(); // Start the UIConsole

     //Main menu
    int choice = uiConsole.start(sc); // Start the UIConsole; choice = uiConsole.displayChoices(sc); // Display the choices
        while (choice != 0) {
            choice = uiConsole.start(sc); // Start the UIConsole  
        }
        sc.close(); // Close the scanner when done
        System.out.println("Exiting the program. Thank you for using the Project Management System!");
    }
}


