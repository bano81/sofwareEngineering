package hellocucumber;

import dtu.ProjectManagementApp.businessLogic.BLController;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import dtu.ProjectManagementApp.businessLogic.Employee;
import dtu.ProjectManagementApp.businessLogic.Project;
import dtu.ProjectManagementApp.businessLogic.Activity;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

public class AssignEmployeeToActivitySteps {
    private Exception e;
    private String employeeId;
    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private BLController blController = new BLController(systemStorage);
    private String projectID;
    private String timeRegistrationId;
    private int timeRegistrationCount;

    @Before
    public void resetLoginState() {
        // Reset login state before each scenario
        systemStorage.resetLoginState();
    }
    @And("an employee with ID {string} exists")
    public void anEmployeeWithIDExists(String employeeId) {
        systemStorage.addEmployee(new Employee("john", "doe", employeeId));
        assertTrue(systemStorage.getEmployees().contains(systemStorage.getEmployee(employeeId)));

    }

    @When("the logged in employee assigns the activity with ID {string} from project with ID {string} to the employee with ID {string}")
    public void theLoggedInEmployeeAssignsTheActivityFromProjectWithIDToTheEmployeeWithID(String activityId, String projectId, String employeeId) {
        try {
            blController.assignEmployeeToActivity(activityId, employeeId);
        } catch (Exception e) {
            this.e = e;
        }
    }

    @Then("the activity {string} from project with ID {string} should be assigned to the employee with ID {string}")
    public void theActivityFromProjectWithIDShouldBeAssignedToTheEmployeeWithID(String activityId, String projectId, String employeeId) {
        assertTrue(systemStorage.getProject(projectId).getActivities().stream()
                .filter(activity -> activity.getActivityId().equals(activityId))
                .anyMatch(activity -> activity.getAssignedEmployees().contains(employeeId)));
    }
}
