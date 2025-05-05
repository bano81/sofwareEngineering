package hellocucumber;


import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import dtu.ProjectManagementApp.businessLogic.Project;
import dtu.ProjectManagementApp.businessLogic.Activity;
import dtu.ProjectManagementApp.businessLogic.BLController;
import dtu.ProjectManagementApp.businessLogic.Employee;

import static org.junit.jupiter.api.Assertions.*;


 import io.cucumber.java.en.*;


public class ProjectSteps {

	private Exception e;
	private String employeeId;
	private SystemStorage systemStorage = TestContext.getSystemStorage();
	private BLController blController = new BLController(systemStorage);
	
	@When("the user tries to create a new project with name {string} and id {string}")
	public void theUserTriesToCreateANewProjectWithNameAndId(String projectName, String projectId) {
		try {
			blController.createProject(projectId, projectName); // Simulate project creation
		} catch (Exception e) {
			this.e = e; // Capture the exception if thrown
		}
	}

	@Given("a project named {string} with id {string} exists")
	public void aProjectNamedWithIdExists(String projectName, String projectId) {
		systemStorage.getProjects().add(new Project(projectId, projectName)); // Simulate project creation
	}

	@When("an activity named {string} is created for the project with id {string}")
	public void anActivityNamedIsCreatedForTheProjectWithId(String activityName, String projectId) {
		systemStorage.getProject(projectId).addActivity(new Activity(activityName)); // Simulate activity creation
	}

	@Given("a user with id {string} exists")
	public void aUserWithIdExists(String userId) {
		systemStorage.addEmployee(new Employee("test", "test", userId)); // Simulate user creation
	}

	@Given("the user {string} is logged in")
	public void theUserIsLoggedIn(String userId) {
		blController.login(userId); // Simulate user login	
	}


	@Then("the list of activities in the project with id {string} should include {string}")
	public void theListOfActivitiesInTheProjectWithIdShouldInclude(String projectId, String activityName) {
		assertTrue(systemStorage.getProject(projectId).getActivities().stream().anyMatch(a -> a.getActivityName().equals(activityName))); // Check if the activity is in the project
	}

	@Then("the project {string} is created with id {string}")
	public void theProjectIsCreatedWithId(String projectName, String projectId) {
		assertTrue(systemStorage.getProject(projectId).getProjectName().equals(projectName)); // Check if the project name matches the expected name
		assertTrue(systemStorage.getProjects().stream().filter(p -> p.getProjectId().equals(projectId)).toList().size() < 2); // Check if the project ID is unique
	}

	@Then("the system should return the error message {string}")
	public void theSystemShouldReturnTheErrorMessage(String string) {
		assertNotNull(e); // Check if an exception was thrown
		assertEquals(string, e.getMessage()); // Check if the exception message matches the expected message
	}

	@When("{string} is assigned as project manager for the project with id{string}")
	public void isAssignedAsProjectManagerForTheProjectWithId(String userId, String projectId) {
		systemStorage.getProject(projectId).setProjectManager(userId); // Simulate assigning the user as project manager
	}

	@Then("{string} should be listed as project manager for the project with id {string}")
	public void ShouldBeListedAsProjectManagerForTheProjectWithId(String userId, String projectId) {
		assertEquals(userId, systemStorage.getProject(projectId).getProjectManagerId()); // Check if the user is listed as project manager
	}
}


