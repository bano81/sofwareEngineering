package hellocucumber;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import dtu.ProjectManagementApp.businessLogic.ProjectManagementAppBL;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WB_loginSteps {

    SystemStorage systemStorage = new SystemStorage();
    ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);
    String e = "";

    @Given("no user with id {string} exists in the system")
    public void noUserWithIdExistsInTheSystem(String employeeId) {
        assertFalse(blController.employeeExists(employeeId));
    }

    @When("the user with id {string} tries to login")
    public void theUserWithIdTriesToLogin(String employeeId) {
        try {
            blController.login(employeeId);
        } catch (IllegalStateException e) {
            this.e = e.getMessage();
        }
    }

    @Then("the system throws an error message {string}")
    public void theSystemThrowsAnErrorMessage(String errorMessage) {
        assertEquals(this.e, errorMessage);
    }

    @Given("a user with id {string} exists in the system")
    public void aUserWithIdExistsInTheSystem(String employeeId) {
        blController.createEmployee("John", "Doe", employeeId);
        assertTrue(blController.employeeExists(employeeId));
    }

    @Given("the user with id {string} is logged in")
    public void theUserWithIdIsLoggedIn(String employeeId) {
        systemStorage.setLoggedInEmployee(systemStorage.getEmployee(employeeId));
    }
    @Given("no user is logged in")
    public void noUserIsLoggedIn() {
        systemStorage.removeLoggedInEmployee();
    }

    @Then("then the user with id {string} is logged in")
    public void thenTheUserWithIdIsLoggedIn(String employeeId) {
        assertEquals(systemStorage.getLoggedInEmployee().getEmployeeId(), employeeId);
    }
}
