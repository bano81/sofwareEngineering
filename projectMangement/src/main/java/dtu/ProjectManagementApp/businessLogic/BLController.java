package dtu.ProjectManagementApp.businessLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BLController {

    public void login (String emplyeeId) {
        SystemStorage.setLoggedInEmployee(emplyeeId);
    }

    // checkIfEmployeeExists returns True if the employee exists in the database, otherwise False.
	public boolean checkIfEmployeeExists(Map<String, Employee> employees, String name, String surname, String employeeId){
    	boolean employeeExists = false;
    	for (Map.Entry<String, Employee> entry : employees.entrySet()) {							// 1
            Employee employee = entry.getValue();													// 2
            if(employee.getEmployeeId().equals(employeeId)) {										// 3
            	employeeExists = true;																// 4
                break;																				// 5
            }
            if (employee.getName().equals(name) && employee.getSurname().equals(surname)) {			// 6
                employeeExists = true;																// 7
                break;																				// 8
            }
        }
    	return employeeExists;																		// 9
	}
	
	//createNewEmployees returns True if the employee is successfully added to the database and False if the addition fails
	public boolean createNewEmployees(Map<String, Employee> employees, String name, String surname, String emplyeeId) {
		boolean employeeExists = checkIfEmployeeExists(employees, name, surname, emplyeeId); 
		if(!employeeExists) {
			employees.put(emplyeeId, new Employee(name, surname));
		}
		return !employeeExists;
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
