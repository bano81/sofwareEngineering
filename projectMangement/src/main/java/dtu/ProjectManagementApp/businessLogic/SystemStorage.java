package dtu.ProjectManagementApp.businessLogic;

import java.util.ArrayList;
import java.util.List;

public class SystemStorage {

    private static final List<Employee> employees = new ArrayList<>();
    private static final List<Project> projects = new ArrayList<>();
    private static String employeeLoggedIn = "";

    public static void addEmployee(String firstName, String surName, String employeeId) { 
        employees.add(new Employee(employeeId, firstName, surName)); // Create a new employee and add it to the list
    }

    public static void removeEmployee(String employeeId) {
        employees.removeIf(employee -> employee.getEmployeeId().equals(employeeId)); // Remove the employee with the given ID from the list
    }

    public static boolean employeeExists(String employeeId) {
        return employees.stream().
                anyMatch(employee -> employee.getEmployeeId().equals(employeeId));
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

    public static Object getLoggedInEmployee() {
        return employeeLoggedIn;
    }

    public static void getEmployeeList() {
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " " + employee.getSurname() + " " + employee.getEmployeeId());
        }
    }

    

}
