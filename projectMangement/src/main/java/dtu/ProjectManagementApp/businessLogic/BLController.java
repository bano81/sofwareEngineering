package dtu.ProjectManagementApp.businessLogic;

import java.util.Map;

public class BLController {

    public boolean login(String employeeId) {
        if (SystemStorage.employeeExists(employeeId)) {
            SystemStorage.setLoggedInEmployee(employeeId);
            return true; // Login successful
        }
        SystemStorage.setLoggedInEmployee(null);
        return false; // Login failed
    }
    private Employee getLoggedInEmployee() {
        Employee loggedInEmployee = SystemStorage.getLoggedInEmployee();
        if (loggedInEmployee == null) {
            throw new IllegalStateException("No employee is currently logged in.");
        }
        return loggedInEmployee;
    }

    public boolean createNewEmployee(String firstName, String surName, String employeeId) {
        boolean employeeExists = SystemStorage.employeeExists(employeeId, surName, surName); // Check if the employee already exists
        if (!employeeExists) {
            SystemStorage.addEmployee(firstName, surName, employeeId); // Create a new employee
            return true; // Employee created successfully
        }
        return false; // Employee already exists
    }

    public void createNewProject(String projectName) {
        Project project = new Project(projectName);
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

    public void createEmployee(String firstName, String surName, String employeeId) {
        SystemStorage.addEmployee(firstName, surName, employeeId);
    }

    
}
