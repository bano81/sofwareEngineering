package hellocucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import dtu.ProjectManagementApp.businessLogic.Activity;
import dtu.ProjectManagementApp.businessLogic.BLController;
import dtu.ProjectManagementApp.businessLogic.Employee;
import dtu.ProjectManagementApp.businessLogic.Project;


public class ActivitySteps {

    Exception e;
    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private BLController blController = new BLController(systemStorage);

    @Given("a user with ID {string} exists")
    public void aUserWithIDExists(String userId) {
        systemStorage.addEmployee(new Employee("test", "test", userId)); // Simulate user creation
    }

    @Given("the user {string} is loogged in")

    @Given("a project named {string} with ID {string} exists")
    public void aProjectNamedExists(String projectName, String ID) {
        systemStorage.getProjects().add(new Project(ID,projectName)); // Simulate project creation
    }

    @Given("an activity named {string} exists in project with ID {string}")
    public void anActivityNamedExistsInProjectWithID(String activityName, String projectId) {
        systemStorage.getProject(projectId).addActivity(new Activity(activityName)); // Simulate activity creation
    }

    @When("the user adds the employee with ID {string} to the activity {string} from project with ID {string}")
    public void theUserAddsTheEmployeeWithIDToTheActivityFromProjectWithID(String employeeId, String activityName, String projectId) {
        // Simulate adding the employee to the activity
        try {
            systemStorage.getProject(projectId).getActivity(activityName).assignEmployee(employeeId);
        } catch (Exception e) {
            this.e = e; // Store the exception for later verification
        }
    }

    @Then("the activity {string} from project with ID {string} should have the employee with ID {string} assigned")
    public void theActivityFromProjectWithIDShouldHaveTheEmployeeWithIDAssigned(String activityName, String projectId, String employeeId) {
        assertTrue(systemStorage.getProject(projectId).getActivity(activityName).getAssignedEmployees().contains(employeeId)); // Check if the activity has the employee assigned
    }
}
