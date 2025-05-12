package dtu.ProjectManagementApp.businessLogic;

public class Employee {
 private String employeeId;
 private String name;
 private String surname;

 private int numberOfActivities; // Number of activities assigned to the employee
 private final int maxActivities = 20; // Maximum number of activities allowed for an employee
 
 public Employee() {
     // Default constructor
 }

 public Employee(String name, String surname,String employeeId ) {
     this.name = name;
     this.surname = surname;
     this.employeeId = employeeId; // Set employee ID directly
 }

 public Employee(String name, String surname) {
     this.name = name;
     this.surname = surname;
     employeeId = name.toLowerCase().substring(0, 2) + surname.toLowerCase().substring(0, 2); // Generate employee ID based on name and surname
 }

 public String getEmployeeId() {
     return employeeId;
 }   

 public String getName() {
     return name;
 }

 public String getSurname() {
     return surname;
 }
}