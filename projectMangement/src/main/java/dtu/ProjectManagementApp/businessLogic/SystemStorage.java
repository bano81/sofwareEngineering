package dtu.ProjectManagementApp.businessLogic;

import java.util.ArrayList;
import java.util.List;

public class SystemStorage {

    private static final List<Employee> employees = new ArrayList<>();
    private static final List<Project> projects = new ArrayList<>();
    private static Employee employeeLoggedIn = null; // Variable to store the logged-in employee

    public static void intitiateTestUsers(){
        //test user
        employees.add(new Employee("test", "normal", "employee")); // Create a test user
        employees.add(new Employee("test2", "project", "manager")); // Create a test user
        employees.add(new Employee("test3", "admin", "admin")); // Create a test user
    }


    public static void addEmployee(String firstName, String surName, String employeeId) { 
        employees.add(new Employee(employeeId, firstName, surName)); // Create a new employee and add it to the list
    }

    public static boolean employeeExists(String employeeId) {
        return employees.stream().
                anyMatch(employee -> employee.getEmployeeId().equals(employeeId));
    }

    public static boolean employeeExists(String employeeId, String name, String surname) {
        boolean employeeExists = false; // Flag to check if the employee exists
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) { // Check if the employee ID already exists
                employeeExists = true; // Employee ID already exists
                break;
            }
            if (employee.getName().equals(name) && employee.getSurname().equals(surname)) { // Check if the name and surname already exist
                employeeExists = true; // Name and surname already exist
                break;
            }
        }
        return employeeExists; // Return true if the employee exists, false otherwise
        
    }

    public static Employee getEmployee(String employeeId) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    public static void setLoggedInEmployee(String employeeId) {
        if (employeeExists(employeeId)) {
            employeeLoggedIn = getEmployee(employeeId);
        }

    }
    public static Employee getLoggedInEmployee() {
        return employeeLoggedIn;
    }

// ####################### Projects #######################
    public static void addProject(Project project) {
        projects.add(project);
    }
    public static void removeProject(Project project) {
        projects.remove(project);
    }

    public static List<Project> getProjects() {
        return projects;
    }



    public static void getEmployeeList() {
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " " + employee.getSurname() + " " + employee.getEmployeeId());
        }
    }

    

    

}
