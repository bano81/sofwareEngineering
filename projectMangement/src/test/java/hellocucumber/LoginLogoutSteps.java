package hellocucumber;

import static org.junit.jupiter.api.Assertions.*;

import dtu.ProjectManagementApp.businessLogic.ProjectManagementAppBL;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import dtu.ProjectManagementApp.businessLogic.Employee;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginLogoutSteps {

    private Exception e;
    private String employeeId;
    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);
    

    @Given("An employee with ID {string} exists")
    public void anEmployeeWithIDExists(String employeeId) {

        systemStorage.addEmployee(new Employee("John", "Doe", employeeId));
        assertTrue(blController.employeeExists(employeeId));
    }

    @When("I login with ID {string}")
    public void iLoginWithID(String employeeId) {
        try {
            blController.login(employeeId);
        } catch (IllegalStateException e) {
            this.e = e; // Capture the exception if thrown
        }
        this.employeeId = employeeId; // Store the employee ID for later use
    }

    @Then("The employee with ID {string} should be logged in")
    public void theEmployeeWithIDShouldBeLoggedIn(String employeeId) {
        assertEquals(employeeId,systemStorage.getLoggedInEmployee().getEmployeeId());
    }

    @Then("The employee with ID {string} should not be logged in")
    public void theEmployeeWithIDShouldNotBeLoggedIn(String employeeId) {
        assertNotEquals(employeeId,systemStorage.getLoggedInEmployee().getEmployeeId());
    }

    @Given("No employee with ID {string} exists")
    public void noEmployeeWithIDExists(String employeeId) {
        assertFalse(blController.employeeExists(employeeId));
    }

    @Then("The login attempt should fail")
    public void theLoginAttemptShouldFail() {
        assertEquals(this.e.getMessage(), "Employee with ID " + this.employeeId +" does not exist.");
    }

    @When("I logout")
    public void iLogout() {
        blController.logout();
    }

    @Then("There should be no logged-in employee")
    public void thereShouldBeNoLoggedInEmployee() {
        try {
            blController.getLoggedInEmployee();
        } catch (IllegalStateException e) {
            this.e = e; // Capture the exception if thrown
        }
        assertEquals(e.getMessage(), "No employee is currently logged in.");
    }
}
