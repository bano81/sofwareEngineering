package dtu.ProjectManagementApp.businessLogic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BLController {
    private SystemStorage systemStorage;

    public BLController() {
        this.systemStorage = new SystemStorage();
    }

    public BLController(SystemStorage systemStorage) {
        this.systemStorage = systemStorage;
    }


    public boolean login(String employeeId) throws IllegalStateException {
        if (!employeeExists(employeeId)) { // Check if the employee exists
            throw new IllegalStateException("Employee with ID " + employeeId + " does not exist."); // Employee does not exist
        } else if (systemStorage.getLoggedInEmployee() != null) { // Check if an employee is already logged in
            throw new IllegalStateException("An employee is already logged in."); // An employee is already logged in
        } else {
            systemStorage.setLoggedInEmployee(systemStorage.getEmployee(employeeId)); // Set the logged-in employee
            return true; // Login successful
        }
    }

    public void logout() {
        Employee loggedInEmployee = getLoggedInEmployee(); // Get the currently logged-in employee
        if (loggedInEmployee != null) {
            systemStorage.removeLoggedInEmployee();
        } else {
            throw new IllegalStateException("No employee is currently logged in.");
        }
    }

    public boolean employeeExists(String employeeId) {
        return systemStorage.getEmployees().stream().
                anyMatch(employee -> employee.getEmployeeId().equals(employeeId));
    }

    public boolean employeeExists(String employeeId, String name, String surname) {
        boolean employeeExists = false; // Flag to check if the employee exists
        for (Employee employee : systemStorage.getEmployees()) {
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

    public Employee getLoggedInEmployee() {
        Employee loggedInEmployee = systemStorage.getLoggedInEmployee();
        if (loggedInEmployee == null) {
            throw new IllegalStateException("No employee is currently logged in.");
        }
        return loggedInEmployee;
    }

    public boolean createEmployee(String firstName, String surName, String employeeId) {
        boolean employeeExists = employeeExists(employeeId); // Check if the employee already exists
        if (!employeeExists) {
            systemStorage.addEmployee(new Employee(firstName, surName, employeeId)); // Create a new employee
            return true; // Employee created successfully
        }
        return false; // Employee already exists
    }

    public List<Employee> getEmployees() {
        return systemStorage.getEmployees(); // Return the list of employees
    }

    public Employee getEmployee(String employeeId) {
        if (!systemStorage.employeeExists(employeeId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist.");
        } 
        return systemStorage.getEmployee(employeeId);
    }

    public void assignEmployeeToProject(String projectID, String employeeId) {
        Project project = systemStorage.getProject(projectID);// Get the project by name
        if (project != null) {
            Employee employee = systemStorage.getEmployee(employeeId); // Get the employee by ID
            project.addEmployee(systemStorage.getEmployee(employeeId)); // Assign the employee to the project
        } else {
            throw new IllegalArgumentException("Project with name " + projectID + " does not exist.");
        }
    }

    public void createProject(String projectName) {
        Project project = new Project(projectName);
        if (systemStorage.getProjects().stream().anyMatch(p -> p.getProjectId().equals(project.getProjectId()))) { // Check if the project ID already exists
            throw new IllegalArgumentException("Project ID already exists."); // Throw an exception if it does
        }
        systemStorage.addProject(project); // Add the project to the system storage
    }

    public void createProject(String projectId, String projectName) {
        Project project = new Project(projectId, projectName);
        if (systemStorage.getProjects().stream().anyMatch(p -> p.getProjectId().equals(project.getProjectId()))) { // Check if the project ID already exists
            throw new IllegalArgumentException("Project ID already exists."); // Throw an exception if it does
        }
        systemStorage.addProject(project); // Add the project to the system storage
    }

    public List<Project> getProjects() {
        return systemStorage.getProjects(); // Return the list of projects
    }

    public Project getProject(String projectName) {
        return systemStorage.getProject(projectName); // Return the project by ID
    }

    public void createNewActivity(String projectName, String activityName) {
        systemStorage.getProjects().stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .findFirst()
                .ifPresent(project -> {
                    project.addActivity(new Activity(activityName)); // Add the activity to the project  
                });
    }

    public void createNewActivity(String projectName, String activityName, String employeeId) {
        Project project = new Project(projectName); // Create a new project
        Activity activity = new Activity(activityName); // Create a new activity
        project.addActivity(activity); // Add the activity to the project
        systemStorage.addProject(project); // Add the project to the system storage
    }

    public List<Activity> getActivities() {
        return systemStorage.getProjects().stream()
                .flatMap(project -> project.getActivities().stream())
                .toList(); // Return the list of all activities
    }
    

    public List<Activity> getActivitiesByProject(String projectName) {
        return systemStorage.getProjects().stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .flatMap(project -> project.getActivities().stream())
                .toList(); // Return the list of activities for the specified project
    }


    
    public int getNumberOfNotCompletedActivities(Map<String, Employee> employees, String employeeId) {
		countNumberOfActivities(employeeId);
		return employees.get(employeeId).getNumberOfActivities();
	}

    public int countNumberOfActivities(String employeeId){
        int sum = 0; // Initialize the sum to 0
        for (Project project : systemStorage.getProjects()) { // Iterate through all projects
            for (Activity activity : project.getActivities()) { // Iterate through all activities in the project
                if (activity.getAssignedEmployees().contains(employeeId)){
                    sum += 1;
                }
            }
        }
        return sum; // Return the total number of activities
    }
    /* TIME REGISTRATION */
    public void registerTime(String activityId, String dateString, String hoursSpent, String description) {
        Employee employee = systemStorage.getLoggedInEmployee();
        if (employee == null) {
            throw new IllegalStateException("No user is logged in");
        }

        Project project = systemStorage.getProjects().stream()
                .filter(p -> p.getActivities().stream()
                        .anyMatch(a -> a.getActivityId().equals(activityId)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Project with activity ID " + activityId + " does not exist"));

        Activity activity = project.getActivityById(activityId);

        // Check if employee is assigned to activity
        if (!activity.getAssignedEmployees().contains(employee.getEmployeeId())) {
            throw new IllegalArgumentException("You are not assigned to this activity");
        }

        // Parse date and hours
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateOfRegistration = LocalDate.parse(dateString, formatter);
            double hours = Double.parseDouble(hoursSpent);

            // Create and add time registration
            TimeRegistration timeReg = new TimeRegistration(
                    employee, project, activity, dateOfRegistration, hours, description);
            systemStorage.addTimeRegistration(timeReg);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid hours format");
        }

    }

}
