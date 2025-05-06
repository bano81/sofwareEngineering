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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeRegistrationSteps {
    private Exception e;
    private String employeeId;
    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private BLController blController = new BLController(systemStorage);
    private String projectID;

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
    public void aProjectWithIDExists(String projectName, String projectID, String projectDeadLine) {
        this.projectID = projectID;
        systemStorage.getProjects().add(new Project(projectID, projectName, projectDeadLine));
        assertTrue(systemStorage.getProjects().contains(systemStorage.getProject(projectID)));
    }
    @And("an activity {string} with ID {string} exists")
    public void anActivityWithIDExists(String activityName, String activityId) {
        systemStorage.getProject(projectID).addActivity(new Activity(activityId, activityName));
        assertTrue(systemStorage.getProject(projectID).getActivities().contains(systemStorage.getProject(projectID).getActivityById(activityId)));

    }
    @And("an activity {string} with ID {string} belongs to project {string}")
    public void anActivityWithIDBelongsToProject(String name, String ID, String projectID) {
        assertTrue(systemStorage.getProject(projectID).getActivities().contains(systemStorage.getProject(projectID).getActivityById(ID)));
    }

    @And("the employee with ID {string} is assigned to activity with {string}")
    public void theEmployeeWithIDIsAssignedToActivity(String employeeId, String activityID) {
       // systemStorage.addEmployee(new Employee("john", "doe", employeeId));
       // systemStorage.getProject(projectID).getActivityById(activityID).assignEmployee(employeeId);
       // assertTrue(systemStorage.getProject(projectID).getActivityById(activityID).isEmployeeAssigned(employeeId));
    }

    @When("a user registers time spent on activity with Id {string}, on date {string}, hours spent {string}, and description {string}")
    public void aUserRegistersTimeSpentOnActivityWithIdOnDateHoursSpentAndDescription(String arg0, String arg1, String arg2, String arg3) {
        throw new io.cucumber.java.PendingException();
    }


    @Then("the time registration should be saved to SystemStorage")
    public void theTimeRegistrationShouldBeSavedToSystemStorage() {
        throw new io.cucumber.java.PendingException();
    }

    @And("the system should display a success message {string}")
    public void theSystemShouldDisplayASuccessMessage(String arg0) {
        throw new io.cucumber.java.PendingException();
    }

    @Then("the time registration should not be saved to SystemStorage")
    public void theTimeRegistrationShouldNotBeSavedToSystemStorage() {
        throw new io.cucumber.java.PendingException();
    }

    @And("the system should display an error message {string}")
    public void theSystemShouldDisplayAnErrorMessage(String arg0) {
        throw new io.cucumber.java.PendingException();
    }

    @Given("No user is logged in")
    public void noUserIsLoggedIn() {
        throw new io.cucumber.java.PendingException();
    }

    @When("a user tries to register time with activityId {string}, date {string}, hours {string}, and description {string}")
    public void aUserTriesToRegisterTimeWithActivityIdDateHoursAndDescription(String arg0, String arg1, String arg2, String arg3) {
        throw new io.cucumber.java.PendingException();
    }



}
