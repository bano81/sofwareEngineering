package dtu.ProjectManagementApp.businessLogic;

import java.util.Map;

public class BLController {

    public boolean login(String employeeId) throws IllegalStateException {
        if (!employeeExists(employeeId)) { // Check if the employee exists
            throw new IllegalStateException("Employee with ID " + employeeId + " does not exist."); // Employee does not exist
        } else if (SystemStorage.getLoggedInEmployee() != null) { // Check if an employee is already logged in
            throw new IllegalStateException("An employee is already logged in."); // An employee is already logged in
        } else {
            SystemStorage.setLoggedInEmployee(SystemStorage.getEmployee(employeeId)); // Set the logged-in employee
            return true; // Login successful
        }
    }

    public void logout() {
        Employee loggedInEmployee = getLoggedInEmployee(); // Get the currently logged-in employee
        if (loggedInEmployee != null) {
            SystemStorage.removeLoggedInEmployee();
        } else {
            throw new IllegalStateException("No employee is currently logged in.");
        }
    }

    public boolean employeeExists(String employeeId) {
        return SystemStorage.getEmployees().stream().
                anyMatch(employee -> employee.getEmployeeId().equals(employeeId));
    }

    public boolean employeeExists(String employeeId, String name, String surname) {
        boolean employeeExists = false; // Flag to check if the employee exists
        for (Employee employee : SystemStorage.getEmployees()) {
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
        Employee loggedInEmployee = SystemStorage.getLoggedInEmployee();
        if (loggedInEmployee == null) {
            throw new IllegalStateException("No employee is currently logged in.");
        }
        return loggedInEmployee;
    }

    public boolean createEmployee(String firstName, String surName, String employeeId) {
        boolean employeeExists = employeeExists(employeeId); // Check if the employee already exists
        if (!employeeExists) {
            SystemStorage.addEmployee(new Employee(firstName, surName, employeeId)); // Create a new employee
            return true; // Employee created successfully
        }
        return false; // Employee already exists
    }

    public void createProject(String projectName) {
        Project project = new Project(projectName);
        if (!SystemStorage.getLoggedInEmployee().isAdmin()) { // Check if the logged-in employee is an admin
            throw new IllegalArgumentException("Insufficient permissions to create a project"); // Throw an exception if not an admin
        }
        if (SystemStorage.getProjects().stream().anyMatch(p -> p.getProjectId().equals(project.getProjectId()))) { // Check if the project ID already exists
            throw new IllegalArgumentException("Project ID already exists."); // Throw an exception if it does
        }
        SystemStorage.addProject(project); // Add the project to the system storage
    }

    public void createNewActivity(String projectName, String activityId, String activityName) {
        SystemStorage.getProjects().stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .findFirst()
                .ifPresent(project -> {
                    project.addActivity(new Activity(activityId, activityName)); // Add the activity to the project  
                });
    }


        
    /*// addNewActivityToProject adds an activity to the employee's list of activities. It does not assign the activity to a project.
	public void addNewActivityToProject( Map<String, Employee> employees, String employeeId, String activityId, 
			String activityName, Date startDate, Date endDate, double activityBudgtedhour, String activityStatus) {
        employees.get(employeeId).setActivity(activityId, new Activity(activityName, startDate, endDate, activityBudgtedhour));
        employees.get(employeeId).getActivity(activityId).setActivityStatus(activityStatus);
        employees.get(employeeId).sortActivitiesByDate();
    }*/

    public int getNumberOfNotCompletedActivities(Map<String, Employee> employees, String employeeId) {
		employees.get(employeeId).countNumberOfActivities();
		return employees.get(employeeId).getNumberOfActivities();
	}

<<<<<<< HEAD
    public void createEmployee(String firstName, String surName, String employeeId) {
        SystemStorage.addEmployee(firstName, surName, employeeId);
    }

    public static Employee getEmployee(String employeeId) {
        if (!SystemStorage.employeeExists(employeeId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist.");
        } 
        return SystemStorage.getEmployee(employeeId);
    }
=======
>>>>>>> be78a0a5b2e430fe4b1178781c783858c7293b00
    
}
