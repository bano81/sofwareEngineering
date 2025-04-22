package hellocucumber;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import dtu.example.ui.CLIEngine;
import dtu.example.ui.Employee;
import io.cucumber.java.en.*;

public class ProjectSteps {
	
	CLIEngine cliEngine = new CLIEngine();
	Map<String, Employee> employees = new HashMap<>(); // List to store employees
	int choice = 1;
	String nameStr, surnameStr, employeeIdStr;
	
	@When("an employee is created with name {string}, surname {string} and ID {string}")
	public void anEmployeeIsCreatedWithName(String name, String surname, String employeeId) {
		cliEngine.creatNewEmployees(employees, name, surname, employeeId);
	}
	
	
	@Then("show the list of employees contains an employee with that name, surname and ID")
	public void theListOfEmployeesContainsAnEmployeeWithThatNameSurnameAndID() {
		cliEngine.creatNewEmployees(employees, "John", "Smith", "jhsm");
		cliEngine.creatNewEmployees(employees, "Anne", "Andersen", "anan");
		cliEngine.getlistOfEmployees(employees);
	    
	}
	
	
	@When("an employee is created with name {string}, surname {string} and a used ID {string}")
	public void anEmployeeIsCreatedWithNameSurnameAndAUsedID(String string, String string2, String string3) {
		nameStr = string;
		surnameStr = string2;
		employeeIdStr = string3;
	}

	@Then("the system will return an error message.")
	public void theSystemWillReturnAnErrorMessage() {
		System.out.println(" ");
		boolean state = cliEngine.creatNewEmployees(employees, nameStr, surnameStr, employeeIdStr);
		assertFalse(state);
		cliEngine.creatNewEmployees(employees, nameStr, surnameStr, employeeIdStr);
	    
	}
	
	@When("an employee with ID {string} add activity with ID {string}, name {string}, start date {string}, end date {string}, budget hours {int} and status {string}")
	public void anEmployeeWithIDAddActivityWithIDNameStartDateEndDateBudgetHoursAndStatus(String employeeId, String activityId, String activityName, String startDate, String endDate, Integer activityBudgtedhour, String activityStatus) {
		//addNewActivityToProject(employees, employeeId, activityId, activityName, startDate, endDate, activityBudgtedhour, activityStatus); // Create a new activity
	}

	@Then("show the list of activities")
	public void showTheListOfActivities() {
	    
	}
}


