package dtu.ProjectManagementApp.businessLogic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProjectManagementAppBL {
    private SystemStorage systemStorage;

    public ProjectManagementAppBL() {
        this.systemStorage = new SystemStorage();
    }

    public ProjectManagementAppBL(SystemStorage systemStorage) {
        this.systemStorage = systemStorage;
    }


    public void login(String employeeId) throws IllegalStateException {
        if (!employeeExists(employeeId)) { // Check if the employee exists
            throw new IllegalStateException("Employee with ID " + employeeId + " does not exist."); // Employee does not exist
        } else if (systemStorage.getLoggedInEmployee() != null) { // Check if an employee is already logged in
            if (systemStorage.getLoggedInEmployee().getEmployeeId().equals(employeeId)) {
                throw new IllegalStateException("You are already logged in."); // The same employee is already logged in
            }
            throw new IllegalStateException("Another employee is already logged in."); // An employee is already logged in
        } else {
            systemStorage.setLoggedInEmployee(systemStorage.getEmployee(employeeId)); // Set the logged-in employee
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

    public void createProject(String projectName) {
        String projectId = String.valueOf(LocalDate.now().getYear()).substring(2) + String.format("%03d", systemStorage.getProjects().size() + 1);
        int projectIdInt = Integer.parseInt(projectId);
        while (systemStorage.getProjects().stream().anyMatch(p -> p.getProjectId().equals(projectId))) { // Check if the project ID already exists
            projectIdInt++;
        }
        Project project = new Project(projectName, String.valueOf(projectIdInt)); // Create a new project
        systemStorage.addProject(project); // Add the project to the system storage
    }


    public void createNewActivity(String projectName, String activityName, String startWeek, String endWeek, double budgetedHours) {
        systemStorage.getProjects().stream()
                .filter(project -> project.getProjectId().equals(projectName))
                .findFirst()
                .ifPresent(project -> {
                    project.addActivity(new Activity(activityName,startWeek, endWeek, budgetedHours)); // Add the activity to the project  
                });
    }

    /* TIME REGISTRATION */
    public void registerTime( String activityId, String dateString, String hoursSpent, String description) {
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

        if (!activity.getAssignedEmployees().contains(employee.getEmployeeId())) {
            throw new IllegalArgumentException("You are not assigned to this activity");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateOfRegistration = LocalDate.parse(dateString, formatter);

            double hours = Double.parseDouble(hoursSpent);

            if (hours <= 0.0 || hours > 24.0) {
                throw new IllegalArgumentException("Invalid hours format, must be between 0 and 24");
            }

            TimeRegistration timeReg = new TimeRegistration(
                    employee, project, activity, dateOfRegistration, hours, description);
            systemStorage.addTimeRegistration(timeReg);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid hours format, must be between 0 and 24");
        }

    }
    public void editTimeRegistration(String timeRegistrationId, String newHours,  String newDescription) {

        TimeRegistration timeRegistration = systemStorage.getTimeRegistrations().stream()
                .filter(tr -> tr.getTimeRegistrationId().equals(timeRegistrationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Time registration with ID " + timeRegistrationId + " does not exist."));

        if (newHours != null) {
            double hours = Double.parseDouble(newHours);
            timeRegistration.setHoursSpent(hours);
        }

        if (newDescription != null) {
            try {
                timeRegistration.setDescription(newDescription);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Description cannot be null or empty");
            }
        }
    }
    public void deleteTimeRegistration(String timeRegistrationId) {
        TimeRegistration timeRegistration = systemStorage.getTimeRegistrations().stream()
                .filter(tr -> tr.getTimeRegistrationId().equals(timeRegistrationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Time registration ID does not exist."));

        systemStorage.removeTimeRegistration(timeRegistration);
    }


    public void assignEmployeeToActivity(String activityId, String huba) {
        Project project = systemStorage.getProjects().stream()
                .filter(p -> p.getActivities().stream()
                        .anyMatch(a -> a.getActivityId().equals(activityId)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Project with activity ID " + activityId + " does not exist"));
        project.getActivityById(activityId).assignEmployee(huba);
    }

    public void removeEmployeeFromActivity(String activityId, String employeeID) {
        Project project = systemStorage.getProjects().stream()
                .filter(p -> p.getActivities().stream()
                        .anyMatch(a -> a.getActivityId().equals(activityId)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Project with activity ID " + activityId + " does not exist"));
                project.getActivityById(activityId).removeEmployee(employeeID); // Remove the employee from the activity
    }

    public void assignProjectManager(String projectId, String employeeId) {
        Project project = systemStorage.getProject(projectId); // Get the project by ID
        if (project != null) {
            project.setProjectManager(employeeId); // Set the employee as the project manager
        } else {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }
    }


}
