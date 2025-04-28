package dtu.ProjectManagementApp.businessLogic;

import java.util.ArrayList;
import java.util.List;

public class SystemStorage {

    private static final List<Employee> employees = new ArrayList<>();
    private static final List<Project> projects = new ArrayList<>();
    private static String employeeLoggedIn = "";

    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }
    public static void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public static boolean employeeExists(String employeeId) {
        return employees.stream().
                anyMatch(employee -> employee.getEmployeeId().equals(employeeId));
    }

    public static boolean employeeExists2(String employeeId, String name, String surname) {
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
        employeeLoggedIn = employeeId; // Set the employee's logged-in status to true
    }


}
