package hellocucumber;

import dtu.ProjectManagementApp.businessLogic.ProjectManagementAppBL;
import dtu.ProjectManagementApp.businessLogic.Employee;
import dtu.ProjectManagementApp.businessLogic.Project;
import dtu.ProjectManagementApp.businessLogic.Activity;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class TimeRegistrationSteps {
    private Exception e;
    private String employeeId;
    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);
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


    @And("the latest time registration should have employee {string}")
    public void theLatestTimeRegistrationShouldHaveEmployee(String employeeId) {
        assertEquals(employeeId, systemStorage.getTimeRegistrations().getLast().getEmployee().getEmployeeId());
    }

    @And("the latest time registration should be for activity {string}")
    public void theLatestTimeRegistrationShouldBeForActivity(String activityId) {
        assertEquals(activityId, systemStorage.getTimeRegistrations().getLast().getActivity().getActivityId());
    }

    @And("the latest time registration should have date {string}")
    public void theLatestTimeRegistrationShouldHaveDate(String dateString) {
        assertEquals(dateString, systemStorage.getTimeRegistrations().getLast().getDate().toString());
    }

    @And("the latest time registration should have {string} hours spent")
    public void theLatestTimeRegistrationShouldHaveHoursSpent(String hoursSpent) {
        assertEquals(Double.parseDouble(hoursSpent), systemStorage.getTimeRegistrations().getLast().getHoursSpent());
    }

    @And("the latest time registration should have description {string}")
    public void theLatestTimeRegistrationShouldHaveDescription(String description) {
        assertEquals(description, systemStorage.getTimeRegistrations().getLast().getDescription());
    }

    @And("the employee with ID {string} is not assigned to activity with {string}")
    public void theEmployeeWithIDIsNotAssignedToActivityWith(String employeeId, String activityId) {
        assertFalse(systemStorage.getProject(projectID).getActivityById(activityId).getAssignedEmployees().contains(employeeId));
    }


    //Edit time registration
    @And("a time registration for activity with Id {string}, on date {string}, hours spent {string}, and description {string} exists")
    public void aTimeRegistrationForActivityWithIdOnDateHoursSpentAndDescriptionExists(String activityId, String dateString, String hoursSpent, String description) {
        blController.registerTime(activityId, dateString, hoursSpent, description);
        timeRegistrationId = systemStorage.getTimeRegistrations().getLast().getTimeRegistrationId();
        if (timeRegistrationId != null) {
            assertTrue(systemStorage.getTimeRegistrations().stream().anyMatch(tr -> tr.getTimeRegistrationId().equals(timeRegistrationId)));
        } else {
            fail("Failed to create time registration");
        }
    }

    @When("a user edits the time registration for activity with Id {string}, on date {string}, hours spent {string}, and description {string}")
    public void aUserEditsTheTimeRegistrationForActivityWithIdOnDateHoursSpentAndDescription(String activityId, String dateString, String hoursSpent, String Description) {
        timeRegistrationCount = systemStorage.getTimeRegistrations().size();
        try {
            blController.editTimeRegistration(timeRegistrationId, hoursSpent, Description);
        } catch (Exception e) {
            this.e = e;
        }
    }

    @When("a user deletes the time registration for activity with Id {string}, on date {string}")
    public void aUserDeletesTheTimeRegistrationForActivityWithIdOnDate(String activityId, String dateString) {
        timeRegistrationCount = systemStorage.getTimeRegistrations().size();
        try {
            blController.deleteTimeRegistration(timeRegistrationId);
        } catch (Exception e) {
            this.e = e;
        }

    }

    @Then("the time registration should be deleted from SystemStorage")
    public void theTimeRegistrationShouldBeDeletedFromSystemStorage() {
        assertTrue(systemStorage.getTimeRegistrations().size() < timeRegistrationCount);
    }

    @Then("the time registration should not be deleted from SystemStorage")
    public void theTimeRegistrationShouldNotBeDeletedFromSystemStorage() {
        assertFalse(systemStorage.getTimeRegistrations().size() < timeRegistrationCount);
    }
}
