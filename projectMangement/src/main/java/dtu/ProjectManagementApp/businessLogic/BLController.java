package dtu.ProjectManagementApp.businessLogic;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class BLController {

    public boolean login (Map<String, Employee> employees, Scanner sc) {
        System.out.print("Please enter your username: ");
        String username = sc.nextLine();
        System.out.print("Please enter your password: ");
        String password = sc.nextLine();
        boolean isLoggedIn = false; // Flag to check if the user is logged in
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome " + employee.getName() + " " + employee.getSurname() + "!");
                isLoggedIn = true;
                break;
            }
        }
        if (!isLoggedIn) {
            System.out.println("Invalid username or password. Please try again.");
        }
        return isLoggedIn;        
    }

    public void login2(String employeeId) {
        if (SystemStorage.employeeExists(employeeId)){
            SystemStorage.setLoggedInEmployee(employeeId); // Set the employee's logged-in status to true
        } // Set the employee's logged-in status to true
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

    
}
