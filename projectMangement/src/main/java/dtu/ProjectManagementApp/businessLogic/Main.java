package dtu.ProjectManagementApp.businessLogic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Map<String, Employee> employees = new HashMap<>(); // Create a map to store employees
    private static List<Project> projects = new ArrayList<>();
    //private static CLIEngine cliEngine = new CLIEngine(); // Create an instance of CLIEngine
    private static UIConsole uiConsole = new UIConsole(); // Create an instance of UIConsole
    
 public static void main(String[] args) throws ParseException {
    Scanner sc = new Scanner(System.in); // Create a scanner for user input
    System.out.println("Welcome to the Project Management System!");

    SystemStorage.intitiateTestUsers(); // JUST FOR TESTING PURPOSES, DELETE LATER
    System.out.println("ID: admin, manager, employee"); // JUST FOR TESTING PURPOSES, DELETE LATER

     // Login process
     uiConsole.displayLogin(sc);   // test ID: admin, manager, employee
     //uiConsole.start(); // Start the UIConsole

     //Main menu
    int choice = uiConsole.start(sc); // Start the UIConsole; choice = uiConsole.displayChoices(sc); // Display the choices
        while (choice != 0) {
            /*uiConsole.executeChoice(choice, sc);
            System.out.println("");
            choice = uiConsole.displayChoices(sc); // Display the choices again   */
            choice = uiConsole.start(sc); // Start the UIConsole  
        }
        sc.close(); // Close the scanner when done
        System.out.println("Exiting the program. Thank you for using the Project Management System!");
    }

}


