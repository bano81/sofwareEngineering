package dtu.example.ui;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class User {

 public static void main(String[] args) throws ParseException {
     CLIEngine cliEngine = new CLIEngine(); // Create an instance of CLIEngine
      Map<String, Employee> employees = new HashMap<>(); // List to store employees

     
     int choice = cliEngine.displayChoices(); // Display the choices again
     

     String emplyeeId = ""; // Example employee ID
     while (choice != 9) {
         cliEngine.executeChoice(choice, employees); // Execute the choice made by the user
         System.out.println("");
         choice = cliEngine.displayChoices(); // Display the choices again
     }

 }
}

