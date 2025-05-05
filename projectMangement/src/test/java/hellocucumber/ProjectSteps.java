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
	private String projectID;
	
	@Given("an admin user exists")
	public void anAdminUserExists() {
		systemStorage.addEmployee(new Employee("admin", "admin", "admin")); // Simulate admin user creation
		systemStorage.getEmployee("admin").setAdmin(true); // Set the user as admin
	}

	@Given("an admin user is logged in")
	public void anAdminUserIsLoggedIn() {
		blController.login("admin"); // Simulate admin login
		assertTrue(systemStorage.getLoggedInEmployee().isAdmin());
	}

	@When("the user tries to create a new project with name {string}")
	public void theUserTriesToCreateANewProjectWithName(String projectName) {
		try {
			blController.createProject(projectName); // Simulate project creation
		} catch (Exception e) {
			this.e = e; // Capture the exception if thrown
		}
		projectID = systemStorage.getProjects().get(0).getProjectId(); // Store the project ID for later use
	}

	@Given("a project named {string} exists")
	public void aProjectNamedExists(String projectName) {
		systemStorage.getProjects().add(new Project(projectName)); // Simulate project creation
		projectID = systemStorage.getProjects().get(0).getProjectId(); // Store the project ID for later use
	}

	@When("an activity named {string} is created for project {string}")
	public void anActivityNamedIsCreatedForProject(String activityName, String string2) {
		systemStorage.getProject(projectID).addActivity(new Activity(activityName)); // Simulate activity creation
	}

	@Given("a user with id {string} exists")
	public void aUserWithIdExists(String userId) {
		systemStorage.addEmployee(new Employee("test", "test", userId)); // Simulate user creation
	}

	@Given("the user {string} is logged in")
	public void theUserIsLoggedIn(String userId) {
		blController.login(userId); // Simulate user login	
	}


	@Then("the list of activities in {string} should include {string}")
	public void theListOfActivitiesInShouldInclude(String projectName, String activityName) {
		assertTrue(systemStorage.getProject(projectID).getActivities().stream().anyMatch(a -> a.getActivityName().equals(activityName))); // Check if the activity is in the project
	}

	@Then("the project {string} is created with a unique ID")
	public void theProjectIsCreatedWithAUniqueID(String ProjectName) {
		assertTrue(systemStorage.getProject(projectID).getProjectName().equals(ProjectName)); // Check if the project name matches the expected name
		assertTrue(systemStorage.getProjects().stream().filter(p -> p.getProjectId().equals(projectID)).toList().size() < 2); // Check if the project ID is unique
	}

	@Then("the system should return the error message {string}")
	public void theSystemShouldReturnTheErrorMessage(String string) {
		assertNotNull(e); // Check if an exception was thrown
		assertEquals(string, e.getMessage()); // Check if the exception message matches the expected message
	}

	@When("the admin assigns {string} as project manager for {string}")
	public void theAdminAssignsAsProjectManagerFor(String userId, String string) {
		systemStorage.getProject(projectID).setProjectManager(userId); // Simulate assigning the user as project manager
	}

	@Then("{string} should be listed as project manager for {string}")
	public void ShouldBeListedAsProjectManagerFor(String userId, String string) {
		assertEquals(userId, systemStorage.getProject(projectID).getProjectManagerId()); // Check if the user is listed as project manager
	}
}


