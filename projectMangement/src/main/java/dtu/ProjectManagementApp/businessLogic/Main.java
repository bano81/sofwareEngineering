package dtu.ProjectManagementApp.businessLogic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dtu.ProjectManagementApp.ui.CLIEngine;

public class Main {

    private static Map<String, Employee> employees = new HashMap<>(); // Create a map to store employees
    private static List<Project> projects = new ArrayList<>();
    private static CLIEngine cliEngine = new CLIEngine(); // Create an instance of CLIEngine
    
 public static void main(String[] args) throws ParseException {
    Scanner sc = new Scanner(System.in);
    System.out.println("Welcome to the Project Management System!");
    int choice = cliEngine.displayChoices(sc); // Display the choices
     
        while (choice != 0) {
            cliEngine.executeChoice(choice, employees,sc); // Execute the choice made by the user
            System.out.println("");
            choice = cliEngine.displayChoices(sc); // Display the choices again
        
        }
        sc.close(); // Close the scanner when done
        System.out.println("Exiting the program. Thank you for using the Project Management System!");
    }

}


