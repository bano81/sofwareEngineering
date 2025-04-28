package hellocucumber;
<<<<<<< HEAD
/*
import static org.junit.jupiter.api.Assertions.*;
=======

// import static org.junit.jupiter.api.Assertions.*;
>>>>>>> ed79a8e4da4743553d7b947990a68fcb8643da89

// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.*;

// import dtu.example.ui.CLIEngine;
// import dtu.example.ui.Employee;
import io.cucumber.java.en.*;
*/

public class ProjectSteps {
<<<<<<< HEAD
	/*
	private CLIEngine cliEngine = new CLIEngine();
	private Map<String, Employee> employees = new HashMap<>(); // List to store employees
	private int choice = 1;
	private String nameStr, surnameStr, employeeIdStr;
	private boolean state;
=======
	
	// private CLIEngine cliEngine = new CLIEngine();
	// private Map<String, Employee> employees = new HashMap<>(); // List to store employees
	// private int choice = 1;
	// private String nameStr, surnameStr, employeeIdStr;
	// private boolean state;
>>>>>>> ed79a8e4da4743553d7b947990a68fcb8643da89
	
	// @When("an employee is created with name {string}, surname {string} and ID {string}")
	// public void anEmployeeIsCreatedWithName(String name, String surname, String employeeId) {
	// 	cliEngine.creatNewEmployees(employees, name, surname, employeeId);
	// }
	
	
	// @Then("show the list of employees contains an employee with that name, surname and ID")
	// public void theListOfEmployeesContainsAnEmployeeWithThatNameSurnameAndID() {
	// 	cliEngine.creatNewEmployees(employees, "John", "Smith", "jhsm");
	// 	cliEngine.creatNewEmployees(employees, "Anne", "Andersen", "anan");
	// 	cliEngine.getlistOfEmployees(employees);
	    
	// }
	
	// @Given("emplyee with name {string}, surname {string} and ID {string} exists")
	// public void emplyeeWithNameSurnameAndID(String string, String string2, String string3) {
	// 	state = cliEngine.checkIfEmployeeExists(employees, string, string2, string3);
	// 	cliEngine.creatNewEmployees(employees, string, string2, string3);
	//     assertFalse(state);
	// }
	
	// @When("an employee is created with name {string}, surname {string} and a used ID {string}")
	// public void anEmployeeIsCreatedWithNameSurnameAndAUsedID(String string, String string2, String string3) {
	// 	nameStr = string;
	// 	surnameStr = string2;
	// 	employeeIdStr = string3;
	// 	state = cliEngine.creatNewEmployees(employees, nameStr, surnameStr, employeeIdStr);
	// 	assertFalse(state);
	// }

	// @Then("the system will return an error message.")
	// public void theSystemWillReturnAnErrorMessage() {
	// 	System.out.println(" ");
	// 	assertFalse(state);
	// 	cliEngine.displayMessage(0, !state);
	    
	// }
	
	// @When("an employee with ID {string} add activity with ID {string}, name {string}, start date {string}, end date {string}, budget hours {int} and status {string}")
	// public void anEmployeeWithIDAddActivityWithIDNameStartDateEndDateBudgetHoursAndStatus(String employeeId, String activityId, String activityName, String startDateStr, String endDateStr, int activityBudgtedhour, String activityStatus) throws ParseException {
	// 	cliEngine.creatNewEmployees(employees, "Hubert", "Baumeister", "huba");
	// 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// 	Date startDate = sdf.parse(startDateStr);
	// 	Date endDate = sdf.parse(endDateStr);
	// 	cliEngine.addNewActivityToProject(employees, employeeId, activityId, activityName, startDate, endDate, activityBudgtedhour, activityStatus);
		
	// 	startDate = sdf.parse("2025-04-22");
	// 	endDate = sdf.parse("2025-04-23");
	// 	cliEngine.addNewActivityToProject(employees, employeeId, "ana2", "analysis", startDate, endDate, 2, "in progross");
		
	// 	startDate = sdf.parse("2025-04-18");
	// 	endDate = sdf.parse("2025-04-22");
	// 	cliEngine.addNewActivityToProject(employees, employeeId, "des1", "design", startDate, endDate, 4, "completed");
	// }

	// @Then("show the list of activities for the employee with ID {string}")
	// public void showTheListOfActivities(String employeeId) {
	// 	System.out.println("");
	// 	cliEngine.displayActivites(employeeId, employees);	   
	// }
	
	
	// @Given("an employee with ID {string} exits")
	// public void anEmployeeWithNameSurnameAndIDHubaExits(String employeeId) throws ParseException {
	// 	state = cliEngine.creatNewEmployees(employees, "Hubert", "Baumeister", "huba");
	// 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// 	Date startDate = sdf.parse("2025-04-21");
	// 	Date endDate = sdf.parse("2025-04-23");
	// 	cliEngine.addNewActivityToProject(employees, employeeId, "ana1", "analysis", startDate, endDate, 2, "In progross");

	// 	startDate = sdf.parse("2025-04-22");
	// 	endDate = sdf.parse("2025-04-23");
	// 	cliEngine.addNewActivityToProject(employees, employeeId, "ana2", "analysis", startDate, endDate, 2, "In progross");
		
	// 	startDate = sdf.parse("2025-04-18");
	// 	endDate = sdf.parse("2025-04-22");
	// 	cliEngine.addNewActivityToProject(employees, employeeId, "des1", "design", startDate, endDate, 4, "Completed");
		
	// 	assertTrue(state);
	// }

	// @When("the user select the employee with ID {string}")
	// public void theUserSelectTheEmployeeWithID(String string) {
	// 	assertTrue(state);	    
	// }

	// @Then("the system should display the number of activities that are not finished and the list of activites for the employee with ID {string}")
	// public void theSystemShouldDisplayTheNumberOfActivitiesThatAreNotFinishedAndTheListOfActivitesForTheEmployee(String employeeId) {
	// 	int numberOfNotCompletedActivities = cliEngine.getNumberOfNotCompletedActivities(employees, employeeId);
	// 	System.out.println("\nNumber of not completed activites: " + numberOfNotCompletedActivities);
	    
	// }

	// @When("the program is run")
	// public void theProgramIsRun() {
	// 	try {
	// 		this.cliEngine.run();
	// 	} catch (ParseException e) {
	// 		e.printStackTrace();
	// 	}
	// }

	// @Then("the list of projects should include {string}")
	// public void theListOfProjectsShouldInclude(String projectName) {
    //     // Print names of all projects in the list
    //     System.out.println("List of projects:");
    //     cliEngine.getProjectList().forEach(project -> System.out.println(project.getProjectName()));
        
    //     // Check if the project name is in the list of projects
	// 	assertTrue(cliEngine.getProjectList().stream()
	// 			.anyMatch(project -> project.getProjectName().equalsIgnoreCase(projectName)),
    //             "Project " + projectName + " not found in the list of projects.");
	// }
	// @Then("the list of activities in {string} should include {string}")
	// public void theListOfActivitiesInShouldInclude(String string, String string2) {
	// 	// Write code here that turns the phrase above into concrete actions
	// 	throw new io.cucumber.java.PendingException();
	// }


	// ####################### project steps #######################

	@Given("an admin user is logged in")
	public void anAdminUserIsLoggedIn() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@When("the user creates a new project with name {string}")
	public void theUserCreatesANewProjectWithName(String string) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the project {string} is created successfully")
	public void theProjectIsCreatedSuccessfully(String string) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Given("a project named {string} exists")
	public void aProjectNamedExists(String string) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Given("an activity named {string} is created for project {string}")
	public void anActivityNamedIsCreatedForProject(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@When("the activity is added to the project")
	public void theActivityIsAddedToTheProject() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}*/
}


