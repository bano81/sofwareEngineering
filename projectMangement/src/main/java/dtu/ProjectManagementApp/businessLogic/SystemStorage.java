package dtu.ProjectManagementApp.businessLogic;

import java.util.ArrayList;
import java.util.List;

public class SystemStorage {

    private static final List<Employee> employees = new ArrayList<>();
    private static final List<Project> projects = new ArrayList<>();
    private static Employee employeeLoggedIn = null; // Variable to store the logged-in employee

    public static void intitiateTestUsers(){
        BLController blController = new BLController(); // Create an instance of BLController
        //test user
        employees.add(new Employee("test", "normal", "employee")); // Create a test user
        employees.add(new Employee("test2", "project", "manager",false, true)); // Create a test user
        employees.add(new Employee("test3", "admin", "admin", true, false)); // Create a test user
        employees.add(new Employee("test4", "normal", "employee2")); // Create a test user

        projects.add(new Project("1", "P1")); // Create a test project
        projects.add(new Project("2", "P2")); // Create a test project
        projects.add(new Project("3", "P3")); // Create a test project
       
        SystemStorage.getProjectByName("P1").addActivity(new Activity(1,"design"));
        SystemStorage.getProjectByName("P1").addActivity(new Activity(2,"analysis"));
        SystemStorage.getProjectByName("P2").addActivity(new Activity(3,"implementation"));

        SystemStorage.getEmployee("manager").setProject(SystemStorage.getProjectByName("P1"));
        SystemStorage.getEmployee("admin").setProject(SystemStorage.getProjectByName("P2"));
        
        blController.assignEmployeeToActivity("P1", "design", "employee"); // Assign employee to activity
        blController.assignEmployeeToActivity("P1", "analysis", "employee2"); // Assign employee to activity
        /*SystemStorage.getEmployee("admin").setActivityList(new Activity(1,"desing"));
        SystemStorage.getEmployee("admin").setActivityList(new Activity(2,"analysis"));
        SystemStorage.getEmployee("admin").setActivityList(new Activity(3,"implementation"));
        SystemStorage.getEmployee("employee").setActivityList(new Activity(1,"desing"));
        SystemStorage.getEmployee("employee2").setActivityList(new Activity(3,"implementation"));*/

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
    
    public static Project getProjectByName(String projectName) {
        return projects.stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .findFirst()
                .orElse(null);
    }
    

    public static void removeProject(String projectID) {
        projects.remove(getProject(projectID));
    }

    public static List<Project> getProjects() {
        return projects;
    }


    public static boolean employeeExists(String employeeId) {
        // Check if the employee ID already exists in the list of employees
        return employees.stream()
                .anyMatch(employee -> employee.getEmployeeId().equals(employeeId));        
    }
    public static void resetLoginState() {
        employeeLoggedIn = null;
    }
}
