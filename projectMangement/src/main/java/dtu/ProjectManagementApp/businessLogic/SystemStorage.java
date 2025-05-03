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

<<<<<<< HEAD
    public static boolean employeeExists(String employeeId) {
        return employees.stream().
                anyMatch(employee -> employee.getEmployeeId().equals(employeeId));
    }

    public static boolean employeeExists(String employeeId, String name, String surname) {
        boolean employeeExists = false; // Flag to check if the employee exists
        for (Employee employee : employees) {                                                   // 1
            if (employee.getEmployeeId().equals(employeeId)) {                                  // 2  
                employeeExists = true;                                                          // 3
                break;
            }
            if (employee.getName().equals(name) && employee.getSurname().equals(surname)) {     // 4
                employeeExists = true;                                                          // 5
                break;
            }
        }
        return employeeExists;                                                                  // 6
        
=======
    public static List<Employee> getEmployees() {
        return employees;
>>>>>>> be78a0a5b2e430fe4b1178781c783858c7293b00
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
<<<<<<< HEAD



    public static void getEmployeeList() {
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " " + employee.getSurname() + " " + employee.getEmployeeId());
        }
    }



    

=======
>>>>>>> be78a0a5b2e430fe4b1178781c783858c7293b00
}
