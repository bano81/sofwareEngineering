package hellocucumber;

import dtu.ProjectManagementApp.businessLogic.BLController;
import dtu.ProjectManagementApp.businessLogic.Employee;
import dtu.ProjectManagementApp.businessLogic.Project;
import dtu.ProjectManagementApp.businessLogic.Activity;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public class TimeRegistrationSteps {
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

    @Given("The employee with ID {string} is logged in")
    public void theEmployeeWithIDIsLoggedIn(String employeeId) {
        //throw new io.cucumber.java.PendingException();
        systemStorage.addEmployee(new Employee("john", "doe", employeeId));
        blController.login(employeeId);
        assertEquals(employeeId, systemStorage.getLoggedInEmployee().getEmployeeId());

    }
    @And("a project {string} with ID {string} and deadline {string} exists")
    public void aProjectWithIDExists(String projectName, String projectId, String projectDeadLine) {
        this.projectID = projectId;
        systemStorage.getProjects().add(new Project(projectID, projectName, projectDeadLine));
        assertTrue(systemStorage.getProjects().contains(systemStorage.getProject(projectID)));
    }
    @And("an activity {string} with ID {string}, in project with id {string} exists")
    public void anActivityWithIDExists(String activityName, String activityId, String projectId) {
        systemStorage.getProject(projectId).addActivity(new Activity(activityId, activityName));
        assertTrue(systemStorage.getProject(projectId).getActivities().contains(systemStorage.getProject(projectId).getActivityById(activityId)));

    }
    @And("an activity {string} with ID {string} belongs to project {string}")
    public void anActivityWithIDBelongsToProject(String name, String ID, String projectID) {
        assertTrue(systemStorage.getProject(projectID).getActivities().contains(systemStorage.getProject(projectID).getActivityById(ID)));
    }

    @And("the employee with ID {string} is assigned to activity with {string}")
    public void theEmployeeWithIDIsAssignedToActivity(String employeeId, String activityID) {
        systemStorage.addEmployee(new Employee("john", "doe", employeeId));
        systemStorage.getProject(projectID).getActivityById(activityID).assignEmployee(employeeId);
        assertTrue(systemStorage.getProject(projectID).getActivityById(activityID).getAssignedEmployees().contains(employeeId));
    }

    @When("a user registers time spent on activity with Id {string}, on date {string}, hours spent {string}, and description {string}")
    public void aUserRegistersTimeSpentOnActivityWithIdOnDateHoursSpentAndDescription(String activityId, String dateString, String hoursSpent, String description) {
        timeRegistrationCount = systemStorage.getTimeRegistrations().size();
        try {
            blController.registerTime(activityId, dateString, hoursSpent, description);
        } catch (Exception e) {
            this.e = e;
        }
    }


    @Then("the time registration should be saved to SystemStorage")
    public void theTimeRegistrationShouldBeSavedToSystemStorage() {
        assertTrue(systemStorage.getTimeRegistrations().size() > timeRegistrationCount);

    }


    @Then("the time registration should not be saved to SystemStorage")
    public void theTimeRegistrationShouldNotBeSavedToSystemStorage() {
        assertFalse(systemStorage.getTimeRegistrations().size() > timeRegistrationCount);
    }

    @And("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String expectedErrorMessage) {
        assertNotNull(e, "No exception was thrown");
        assertTrue(e instanceof Exception, "expected an exception of type Exception");
        assertEquals(expectedErrorMessage, e.getMessage(), "Error message doesn't match expected message");
    }

    @Given("No user is logged in")
    public void noUserIsLoggedIn() {

        assertNull(systemStorage.getLoggedInEmployee());
    }




}
