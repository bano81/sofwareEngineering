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

    public static Employee getEmployee(String employeeId) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }
    public static void setLoggedInEmployee(String employeeId) {
        employeeLoggedIn = employeeId; // Set the employee's logged-in status to true
    }
// ####################### Projects #######################
    public static void addProject(Project project) {
        projects.add(project);
    }
    public static void removeProject(Project project) {
        projects.remove(project);
    }

}
