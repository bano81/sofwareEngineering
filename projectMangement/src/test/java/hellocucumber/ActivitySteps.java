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
    @And("a project named {string} exists")
    public void aProjectNamedExists(String projectName) {
        systemStorage.getProjects().add(new Project(projectName)); // Simulate project creation
    }
    @And("an activity named {string} exists in project {string}")
    public void anActivityNamedExistsInProject(String string, String string2) {
        systemStorage.getProject(string2).addActivity(new Activity(string)); // Simulate activity creation
    }
    @When("the user adds the activity {string} from project with ID {string} to the employee with ID {string}")
    public void theUserAddsTheActivityFromProjectWithIdToTheEmployeeWithID(String activityName, String projectId , String userId) {
        // Simulate adding the activity to the employee

        try {
            systemStorage.getProject(projectId).getActivity(activityName).assignEmployeeToActivity(userId);
        } catch (Exception e) {
            this.e = e; // Store the exception for later verification
        }
        
        
    }
    @Then("the employee with ID {string} should have the activity {string} from project {string} assigned")
    public void theEmployeeWithIDShouldHaveTheActivityFromProjectAssigned(String employeeId, String activityName, String projectName) {
        // Check if the employee has the activity assigned
        Employee employee = systemStorage.getEmployee(employeeId);
        Activity activity = systemStorage.getProject(projectName).getActivity(activityName);
        assertTrue(employee.getActivityList().contains(activity)); // Check if the activity is in the employee's activity list
    }
}
