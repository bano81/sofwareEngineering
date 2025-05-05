package hellocucumber;

import dtu.ProjectManagementApp.businessLogic.BLController;
import dtu.ProjectManagementApp.businessLogic.Employee;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeRegistrationSteps {
    private Exception e;
    private String employeeId;
    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private BLController blController = new BLController(systemStorage);

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


    @And("a project {string} with ID {string} exists")
    public void aProjectWithIDExists(String arg0, String arg1) {

    }

    @And("the employee with ID {string} is assigned to project {string}")
    public void theEmployeeWithIDIsAssignedToProject(String arg0, String arg1) {
        throw new io.cucumber.java.PendingException();
    }

    @And("an activity {string} with ID {string} belongs to project {string}")
    public void anActivityWithIDBelongsToProject(String arg0, String arg1, String arg2) {
        throw new io.cucumber.java.PendingException();
    }

    @And("the employee with ID {string} is assigned to activity {string}")
    public void theEmployeeWithIDIsAssignedToActivity(String arg0, String arg1) {
        throw new io.cucumber.java.PendingException();
    }

    @When("a user registers time with activityId {string}, date {string}, hours {string}, and description {string}")
    public void aUserRegistersTimeWithActivityIdDateHoursAndDescription(String arg0, String arg1, String arg2, String arg3) {
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
