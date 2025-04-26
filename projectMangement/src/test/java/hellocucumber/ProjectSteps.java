package hellocucumber;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		cliEngine.creatNewEmployees(employees, "Baqer", "Nour", "bano");
	}

	@Then("the system will return an error message.")
	public void theSystemWillReturnAnErrorMessage() {
		System.out.println(" ");
		boolean state = cliEngine.creatNewEmployees(employees, nameStr, surnameStr, employeeIdStr);
		assertFalse(state);
		cliEngine.creatNewEmployees(employees, nameStr, surnameStr, employeeIdStr);
	    
	}
	
	@When("an employee with ID {string} add activity with ID {string}, name {string}, start date {string}, end date {string}, budget hours {int} and status {string}")
	public void anEmployeeWithIDAddActivityWithIDNameStartDateEndDateBudgetHoursAndStatus(String employeeId, String activityId, String activityName, String startDateStr, String endDateStr, int activityBudgtedhour, String activityStatus) throws ParseException {
		cliEngine.creatNewEmployees(employees, "Baqer", "Nour", "bano");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(startDateStr);
		Date endDate = sdf.parse(endDateStr);
		cliEngine.addNewActivityToProject(employees, employeeId, activityId, activityName, startDate, endDate, activityBudgtedhour, activityStatus);
		
		startDate = sdf.parse("2025-04-22");
		endDate = sdf.parse("2025-04-23");
		cliEngine.addNewActivityToProject(employees, employeeId, "ana2", "analysis", startDate, endDate, 2, "in progross");
		
		startDate = sdf.parse("2025-04-18");
		endDate = sdf.parse("2025-04-22");
		cliEngine.addNewActivityToProject(employees, employeeId, "des1", "design", startDate, endDate, 4, "completed");
	}

	@Then("show the list of activities for the employee with ID {string}")
	public void showTheListOfActivities(String employeeId) {
		System.out.println("");
		cliEngine.displayActivites(employeeId, employees);	   
	}
}


