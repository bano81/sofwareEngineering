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


    public static void addEmployee(Employee employee) { 
        employees.add(employee); // Create a new employee and add it to the list
    }

    public static List<Employee> getEmployees() {
        return employees;
    }

    public static Employee getEmployee(String employeeId) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    public static void setLoggedInEmployee(Employee employee) {
        employeeLoggedIn = employee; // Set the logged-in employee
    }
    public static void removeLoggedInEmployee() {
        employeeLoggedIn = null; // Remove the logged-in employee
    }
    
    public static Employee getLoggedInEmployee()  {
        return employeeLoggedIn;
    }

// ####################### Projects #######################
    public static void addProject(Project project) {
        projects.add(project);
    }

    public static Project getProject(String projectID) {
        return projects.stream()
                .filter(project -> project.getProjectId().equals(projectID))
                .findFirst()
                .orElse(null);
    }

    public static void removeProject(String projectID) {
        projects.remove(getProject(projectID));
    }

    public static List<Project> getProjects() {
        return projects;
    }
}
