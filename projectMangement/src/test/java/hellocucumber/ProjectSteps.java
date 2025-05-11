package hellocucumber;


import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import dtu.ProjectManagementApp.businessLogic.Project;
import dtu.ProjectManagementApp.businessLogic.Activity;
import dtu.ProjectManagementApp.businessLogic.ProjectManagementAppBL;
import dtu.ProjectManagementApp.businessLogic.Employee;

import static org.junit.jupiter.api.Assertions.*;


 import io.cucumber.java.en.*;


public class ProjectSteps {

	private Exception e;
	private String employeeId;
	private SystemStorage systemStorage = TestContext.getSystemStorage();
	private ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);

	@When("the user tries to create a new project with name {string}")
	public void theUserTriesToCreateANewProjectWithName(String projectName) {
		try {
			blController.createProject(projectName); // Simulate project creation
		} catch (Exception e) {
			this.e = e; // Capture the exception if thrown
		}
	}

	@Given("a project named {string} with id {string} exists")
	public void aProjectNamedWithIdExists(String projectName, String projectId) {
		systemStorage.getProjects().add(new Project(projectId, projectName)); // Simulate project creation
	}

	@Given("a user with id {string} exists")
	public void aUserWithIdExists(String userId) {
		systemStorage.addEmployee(new Employee("test", "test", userId)); // Simulate user creation
	}

	@Given("the user {string} is logged in")
	public void theUserIsLoggedIn(String userId) {
		blController.login(userId); // Simulate user login	
	}

	@Then("the project {string} is created")
	public void theProjectIsCreatedWithId(String projectName) {
        assertEquals(systemStorage.getProjectByName(projectName).getProjectName(), projectName); // Check if the project name matches the expected name
		assertTrue(systemStorage.getProjects().stream().filter(p -> p.getProjectId().equals(systemStorage.getProjectByName(projectName))).toList().size() < 2); // Check if the project ID is unique
	}

	@When("{string} is assigned as project manager for the project with Id {string}")
	public void isAssignedAsProjectManagerForTheProjectWithId(String userId, String projectId) {
		blController.assignProjectManager(projectId, userId); // Simulate assigning a project manager
	}

	@Then("{string} should be listed as project manager for the project with Id {string}")
	public void ShouldBeListedAsProjectManagerForTheProjectWithId(String userId, String projectId) {
		assertEquals(userId, systemStorage.getProject(projectId).getProjectManagerId()); // Check if the user is listed as project manager
	}
}


