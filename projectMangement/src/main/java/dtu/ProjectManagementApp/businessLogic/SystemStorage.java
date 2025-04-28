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

    public static boolean employeeExists(String employeeId) {
        return employees.stream().
                anyMatch(employee -> employee.getEmployeeId().equals(employeeId));
    }
    public static void setLoggedInEmployee(String employeeId) {
        employeeLoggedIn = employeeId; // Set the employee's logged-in status to true
    }

    public static Employee getLoggedInEmployee() {
        return getEmployee(employeeLoggedIn);
    }

    public static void getEmployeeList() {
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " " + employee.getSurname() + " " + employee.getEmployeeId());
        }
    }

    public static Employee getEmployee(String string) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(string))
                .findFirst()
                .orElse(null); // Return the employee with the given ID or null if not found
    }

    

}
