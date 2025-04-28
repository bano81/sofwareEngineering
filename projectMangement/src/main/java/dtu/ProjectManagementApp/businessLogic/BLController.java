package dtu.ProjectManagementApp.businessLogic;

import java.util.Date;
import java.util.Map;

public class BLController {

    public void login (String emplyeeId) {
        SystemStorage.setLoggedInEmployee(emplyeeId);
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

    // addNewActivityToProject adds an activity to the employee's list of activities. It does not assign the activity to a project.
	public void addNewActivityToProject( Map<String, Employee> employees, String employeeId, String activityId, 
			String activityName, Date startDate, Date endDate, double activityBudgtedhour, String activityStatus) {
        employees.get(employeeId).setActivity(activityId, new Activity(activityName, startDate, endDate, activityBudgtedhour));
        employees.get(employeeId).getActivity(activityId).setActivityStatus(activityStatus);
        employees.get(employeeId).sortActivitiesByDate();
    }

    public int getNumberOfNotCompletedActivities(Map<String, Employee> employees, String employeeId) {
		employees.get(employeeId).countNumberOfActivities();
		return employees.get(employeeId).getNumberOfActivities();
	}

    public void createEmployee(String firstName, String surName, String employeeId) {
        SystemStorage.addEmployee(firstName, surName, employeeId);
    }

    
}
