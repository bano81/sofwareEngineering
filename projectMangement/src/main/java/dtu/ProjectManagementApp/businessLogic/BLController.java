package dtu.ProjectManagementApp.businessLogic;

import java.util.List;
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

    public int getLoggedInEmployeeRole() {
        int role; // 0 = admin, 1 = manager, 2 = employee, 3 = no role
        if (SystemStorage.getLoggedInEmployee().isAdmin()) {
            role = 0; // Admin role
        } else if (SystemStorage.getLoggedInEmployee().isProjectManager()) {
            role = 1; // Project Manager role
        } else if (SystemStorage.getLoggedInEmployee().isEmployee() && !SystemStorage.getLoggedInEmployee().isProjectManager() 
                   && !SystemStorage.getLoggedInEmployee().isAdmin()) {
            role = 2; // Employee role
        } else {
            role = 3; // No role assigned           
        }

        return role; // Return the role of the logged-in user
    }

    public List<Employee> getEmployees() {
        return SystemStorage.getEmployees(); // Return the list of employees
    }

    public Employee getEmployee(String employeeId) {
        if (!SystemStorage.employeeExists(employeeId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist.");
        } 
        return SystemStorage.getEmployee(employeeId);
    }

    void assignEmployeeToActivity(String projectName, String activityName, String employeeId) {
        /*SystemStorage.getProjects().stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .flatMap(project -> project.getActivities().stream())
                .filter(activity -> activity.getActivityName().equals(activityName))
                .findFirst()
                .ifPresent(activity -> {
                    Employee employee = SystemStorage.getEmployee(employeeId); // Get the employee by ID
                    activity.assignEmployee(employee); // Assign the employee to the activity
                    employee.setActivity(activity); // Set the activity for the employee
                }); // Assign the employee to the activity*/
        Project project = SystemStorage.getProjectByName(projectName); // Get the project by name
        if (project != null) {
            Activity activity = project.getActivity(activityName); // Get the activity by name
            if (activity != null) {
                SystemStorage.getEmployee(employeeId).setProject(project);
                SystemStorage.getEmployee(employeeId).setActivityList(activity);  // Set the activity for the employee
            } else {
                throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectName + ".");
            }
        } else {
            throw new IllegalArgumentException("Project with name " + projectName + " does not exist.");
        }
    }

    /*public boolean createEmployee(String firstName, String surName, String employeeId){
        if(!SystemStorage.getLoggedInEmployee().isAdmin() && !SystemStorage.getLoggedInEmployee().isProjectManager()) { // Check if the logged-in employee is an admin or project manager
            return false; // Check if the logged-in employee is an admin
        } else if (employeeExists(employeeId, firstName, surName)) { // Check if the employee ID already exists
            return false; // Employee ID already exists
        } else {
            SystemStorage.addEmployee(new Employee(firstName, surName, employeeId)); // Create a new employee
            return true; // Employee created successfully
        }
    } */

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

    public void createProject(String projectId, String projectName) {
        Project project = new Project(projectId, projectName);
        if (!SystemStorage.getLoggedInEmployee().isAdmin()) { // Check if the logged-in employee is an admin
            throw new IllegalArgumentException("Insufficient permissions to create a project"); // Throw an exception if not an admin
        }
        if (SystemStorage.getProjects().stream().anyMatch(p -> p.getProjectId().equals(project.getProjectId()))) { // Check if the project ID already exists
            throw new IllegalArgumentException("Project ID already exists."); // Throw an exception if it does
        }
        SystemStorage.addProject(project); // Add the project to the system storage
    }

    public List<Project> getProjects() {
        return SystemStorage.getProjects(); // Return the list of projects
    }

    public void createNewActivity(String projectName, int activityId, String activityName) {
        SystemStorage.getProjects().stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .findFirst()
                .ifPresent(project -> {
                    project.addActivity(new Activity(activityId, activityName)); // Add the activity to the project  
                });
    }

    public void createNewActivity(String projectName, int activityId, String activityName, String employeeId) {
        Project project = new Project(projectName); // Create a new project
        Activity activity = new Activity(activityId, activityName); // Create a new activity
        project.addActivity(activity); // Add the activity to the project
        SystemStorage.addProject(project); // Add the project to the system storage
        SystemStorage.getEmployee(employeeId).setProject(project);
    }

    public List<Activity> getActivities() {
        return SystemStorage.getProjects().stream()
                .flatMap(project -> project.getActivities().stream())
                .toList(); // Return the list of all activities
        /*List<Project> projects = SystemStorage.getProjects(); // Get the list of projects
        List<Activity> activities = new ArrayList<>(); // Create a new list to store activities
        for (Project project : projects) { // Iterate through each project
            activities.addAll(project.getActivities()); // Add the activities of the project to the list
        }
        return activities; // Return the list of all activities*/
    }

    public List<Activity> getActivitiesByProject(String projectName) {
        return SystemStorage.getProjects().stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .flatMap(project -> project.getActivities().stream())
                .toList(); // Return the list of activities for the specified project
    }

    /*public List<Activity> getActivitiesByEmployee(String employeeId) {
        return SystemStorage.getEmployees().stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .flatMap(employee -> employee.getAllActivities().stream())
                .toList(); // Return the list of activities for the specified employee
    }*/

    public List<Activity> getActivitiesByEmployee(String employeeId) {
        Employee employee = SystemStorage.getEmployee(employeeId); // Get the employee by ID
        return employee.getActivityList();//  getAllActivities(); // Return the list of activities for the specified employee
    }

    public int getNumberOfActivitiesByEmployee(String employeeId) {
        Employee employee = SystemStorage.getEmployee(employeeId); // Get the employee by ID
        employee.countNumberOfActivities(); // Count the number of activities for the employee
        return employee.getNumberOfActivities();// Return the total number of activities for the specified employee
    }



    public int getNumberOfNotCompletedActivities(Map<String, Employee> employees, String employeeId) {
		employees.get(employeeId).countNumberOfActivities();
		return employees.get(employeeId).getNumberOfActivities();
	}




        
    /*// addNewActivityToProject adds an activity to the employee's list of activities. It does not assign the activity to a project.
	public void addNewActivityToProject( Map<String, Employee> employees, String employeeId, String activityId, 
			String activityName, Date startDate, Date endDate, double activityBudgtedhour, String activityStatus) {
        employees.get(employeeId).setActivity(activityId, new Activity(activityName, startDate, endDate, activityBudgtedhour));
        employees.get(employeeId).getActivity(activityId).setActivityStatus(activityStatus);
        employees.get(employeeId).sortActivitiesByDate();
    }*/

    

    /*public void createEmployee(String firstName, String surName, String employeeId) {
        SystemStorage.addEmployee(firstName, surName, employeeId);
    }*/

    

    

    

    
    
}
